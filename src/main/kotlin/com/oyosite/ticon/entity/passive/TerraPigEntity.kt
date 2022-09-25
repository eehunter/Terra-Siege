package com.oyosite.ticon.entity.passive

import com.oyosite.ticon.entity.AccessibleGoalSelector
import com.oyosite.ticon.entity.addGoals
import net.minecraft.block.BlockState
import net.minecraft.entity.*
import net.minecraft.entity.ai.goal.*
import net.minecraft.entity.attribute.DefaultAttributeContainer
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.data.DataTracker
import net.minecraft.entity.data.TrackedData
import net.minecraft.entity.data.TrackedDataHandlerRegistry
import net.minecraft.entity.passive.AnimalEntity
import net.minecraft.entity.passive.PassiveEntity
import net.minecraft.entity.passive.PigEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Items
import net.minecraft.nbt.NbtCompound
import net.minecraft.recipe.Ingredient
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvent
import net.minecraft.sound.SoundEvents
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World

class TerraPigEntity(entityType: EntityType<out TerraPigEntity>, world: World): AnimalEntity(entityType, world), AccessibleGoalSelector, ItemSteerable, Saddleable {
    override val goalSelectorAccess: GoalSelector get() = goalSelector
    private val saddledComponent: SaddledComponent = SaddledComponent(this.dataTracker, BOOST_TIME, SADDLED)
    override fun createChild(world: ServerWorld, entity: PassiveEntity): PassiveEntity = TODO("Not implemented yet")

    override fun initGoals() = addGoals(
        0 to SwimGoal(this),
        1 to EscapeDangerGoal(this, 1.25),
        3 to AnimalMateGoal(this, 1.0),
        4 to TemptGoal(this, 1.2, BREEDING_INGREDIENT, false),
        5 to FollowParentGoal(this, 1.1),
        6 to WanderAroundFarGoal(this, 1.0),
        7 to LookAtEntityGoal(this, PlayerEntity::class.java, 6.0f),
        8 to LookAroundGoal(this),
    )

    fun createTerraPigAttributes(): DefaultAttributeContainer.Builder? = createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25)

    override fun getPrimaryPassenger(): Entity? {
        val entity = this.firstPassenger
        return if (entity != null && canBeControlledByRider(entity)) entity else null
    }

    private fun canBeControlledByRider(entity: Entity): Boolean {
        if (this.isSaddled && entity is PlayerEntity) {
            val playerEntity = entity
            return playerEntity.mainHandStack.isOf(Items.CARROT_ON_A_STICK) || playerEntity.offHandStack.isOf(Items.CARROT_ON_A_STICK)
        }
        return false
    }


    override fun onTrackedDataSet(data: TrackedData<*>) {
        if (BOOST_TIME == data && world.isClient) saddledComponent.boost()
        super.onTrackedDataSet(data)
    }

    override fun initDataTracker() {
        super.initDataTracker()
        dataTracker.startTracking(SADDLED, false)
        dataTracker.startTracking(BOOST_TIME, 0)
    }

    override fun writeCustomDataToNbt(nbt: NbtCompound) {
        super.writeCustomDataToNbt(nbt)
        saddledComponent.writeNbt(nbt)
    }

    override fun readCustomDataFromNbt(nbt: NbtCompound) {
        super.readCustomDataFromNbt(nbt)
        saddledComponent.readNbt(nbt)
    }

    override fun getAmbientSound(): SoundEvent = SoundEvents.ENTITY_PIG_AMBIENT
    override fun getHurtSound(source: DamageSource?): SoundEvent = SoundEvents.ENTITY_PIG_HURT
    override fun getDeathSound(): SoundEvent = SoundEvents.ENTITY_PIG_DEATH
    override fun playStepSound(pos: BlockPos, state: BlockState) = playSound(SoundEvents.ENTITY_PIG_STEP, 0.15f, 1.0f)

    override fun canBeSaddled(): Boolean = this.isAlive && !this.isBaby


    override fun dropInventory() {
        super.dropInventory()
        if (isSaddled) this.dropItem(Items.SADDLE)
    }

    override fun interactMob(player: PlayerEntity, hand: Hand): ActionResult {
        val bl = isBreedingItem(player.getStackInHand(hand))
        if (!bl && isSaddled && !hasPassengers() && !player.shouldCancelInteraction()) {
            if (!world.isClient) player.startRiding(this)
            return ActionResult.success(world.isClient)
        }
        val actionResult = super.interactMob(player, hand)
        if (!actionResult.isAccepted) {
            val itemStack = player.getStackInHand(hand)
            return if (itemStack.isOf(Items.SADDLE)) itemStack.useOnEntity(player, this, hand) else ActionResult.PASS
        }
        return actionResult
    }

    //@get:JvmName("getIsSaddled")
    //val isSaddled: Boolean get() = isSaddled()
    override fun isSaddled(): Boolean = saddledComponent.isSaddled

    override fun saddle(sound: SoundCategory?) {
        saddledComponent.isSaddled = true
        if (sound != null) world.playSoundFromEntity(null, this, SoundEvents.ENTITY_PIG_SADDLE, sound, 0.5f, 1.0f)
    }

    override fun updatePassengerForDismount(passenger: LivingEntity): Vec3d? {
        val direction = this.movementDirection
        if (direction.axis === Direction.Axis.Y) {
            return super.updatePassengerForDismount(passenger)
        }
        val offsets = Dismounting.getDismountOffsets(direction)
        val blockPos = blockPos
        val mutable = BlockPos.Mutable()
        for (entityPose in passenger.poses) {
            val box = passenger.getBoundingBox(entityPose)
            for (js in offsets) {
                var vec3d: Vec3d? = null
                mutable[blockPos.x + js[0], blockPos.y] = blockPos.z + js[1]
                val d = world.getDismountHeight(mutable)
                if (!Dismounting.canDismountInBlock(d) || !Dismounting.canPlaceEntityAt(world, passenger, box.offset(Vec3d.ofCenter(mutable, d).also { vec3d = it }))) continue
                passenger.pose = entityPose
                return vec3d
            }
        }
        return super.updatePassengerForDismount(passenger)
    }

    override fun travel(movementInput: Vec3d?) { travel(this, saddledComponent, movementInput) }

    override fun getSaddledSpeed(): Float = getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED).toFloat() * 0.225f
    
    override fun setMovementInput(movementInput: Vec3d?) = super<AnimalEntity>.travel(movementInput)

    override fun consumeOnAStickItem(): Boolean = saddledComponent.boost(getRandom())



    companion object {
        private val SADDLED = DataTracker.registerData(PigEntity::class.java, TrackedDataHandlerRegistry.BOOLEAN)
        private val BOOST_TIME = DataTracker.registerData(PigEntity::class.java, TrackedDataHandlerRegistry.INTEGER)
        private val BREEDING_INGREDIENT = Ingredient.ofItems(Items.CARROT, Items.POTATO, Items.BEETROOT)
    }
}