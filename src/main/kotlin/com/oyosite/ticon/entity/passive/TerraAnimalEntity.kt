package com.oyosite.ticon.entity.passive

import com.oyosite.ticon.entity.pathingPenalties
import net.minecraft.block.Blocks
import net.minecraft.entity.EntityType
import net.minecraft.entity.SpawnReason
import net.minecraft.entity.ai.pathing.PathNodeType.DAMAGE_FIRE
import net.minecraft.entity.ai.pathing.PathNodeType.DANGER_FIRE
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.passive.PassiveEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.particle.ParticleTypes
import net.minecraft.tag.BlockTags
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.random.Random
import net.minecraft.world.BlockRenderView
import net.minecraft.world.World
import net.minecraft.world.WorldAccess
import net.minecraft.world.WorldView
import java.util.*

@Deprecated("It is unlikely that I will need this, as AnimalEntity is more suitable that I originally thought. I will leave this here for the time being in case it becomes useful.")
abstract class TerraAnimalEntity(entityType: EntityType<out TerraAnimalEntity>, world: World) : PassiveEntity(entityType, world) {
    private var loveTicks = 0
    private var lovingPlayer: UUID? = null
    init{
        pathingPenalties(DANGER_FIRE to 16, DAMAGE_FIRE to -1)

    }

    override fun mobTick() { if (getBreedingAge() != 0) loveTicks = 0; super.mobTick() }
    override fun damage(source: DamageSource?, amount: Float): Boolean = (if (isInvulnerableTo(source)) false else { this.loveTicks = 0;  super.damage(source, amount) })
    override fun tickMovement() {
        super.tickMovement()
        if (getBreedingAge() != 0) loveTicks = 0
        if (loveTicks <= 0) return
        --loveTicks
        if (loveTicks % 10 != 0) return
        val (d,e,f) = Array(3){random.nextGaussian() * 0.02}
        world.addParticle(ParticleTypes.HEART, getParticleX(1.0), this.randomBodyY + 0.5, getParticleZ(1.0), d, e, f)
    }
    override fun getPathfindingFavor(pos: BlockPos, world: WorldView): Float = (if (world.getBlockState(pos.down()).isOf(Blocks.GRASS_BLOCK)) 10.0f else world.getPhototaxisFavor(pos))

    override fun writeCustomDataToNbt(nbt: NbtCompound) {
        super.writeCustomDataToNbt(nbt)
        nbt.putInt("InLove", loveTicks)
        lovingPlayer?.let{nbt.putUuid("LoveCause", it)}
    }
    override fun getHeightOffset(): Double = 0.14
    override fun readCustomDataFromNbt(nbt: NbtCompound) {
        super.readCustomDataFromNbt(nbt)
        loveTicks = nbt.getInt("InLove")
        lovingPlayer = if (nbt.containsUuid("LoveCause")) nbt.getUuid("LoveCause") else null
    }
    open fun isValidNaturalSpawn(type: EntityType<out TerraAnimalEntity>, world: WorldAccess, spawnReason: SpawnReason, pos: BlockPos, random: Random): Boolean = world.getBlockState(pos.down()).isIn(BlockTags.ANIMALS_SPAWNABLE_ON) && isLightLevelValidForNaturalSpawn(world, pos)

    protected open fun isLightLevelValidForNaturalSpawn(world: BlockRenderView, pos: BlockPos?): Boolean =world.getBaseLightLevel(pos, 0) > 8

    override fun getMinAmbientSoundDelay(): Int = 120

}




