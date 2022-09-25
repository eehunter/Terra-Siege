package com.oyosite.ticon.util.datagen

import com.oyosite.ticon.block.BlockRegistry
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider
import net.minecraft.block.Block
import net.minecraft.item.ItemConvertible
import net.minecraft.item.Items.RAW_COPPER
import net.minecraft.item.Items.RAW_IRON
import net.minecraft.loot.LootTable.Builder
import java.util.function.Function

class TerraBlockLootTableGenerator(dataGenerator: FabricDataGenerator): FabricBlockLootTableProvider(dataGenerator) {


    override fun generateBlockLootTables() = BlockRegistry.run{

        TERRATHIL_STONE.silkable drops TERRATHIL_COBBLESTONE
        TERRATHIL_COPPER_ORE.silkable drops RAW_COPPER
        TERRATHIL_IRON_ORE.silkable drops RAW_IRON

        TERRA_LOG drops ITSELF
        TERRA_PLANKS drops ITSELF
        TERRA_LEAVES drops leaves(TERRA_SAPLING, SAPLING_DROP_CHANCE)


    }



    private infix fun Block.drops(self: ITSELF) = +this
    private infix fun Block.drops(item: ItemConvertible) = +(this to item)
    private infix fun Block.drops(lootTable: Builder) = +(this to lootTable)
    private infix fun Block.drops(lootTableSupplier: (Block)->Builder) = +(this to lootTableSupplier)
    private infix fun Silkable.drops(item: ItemConvertible) = this.block.let{addDropWithSilkTouch(it);+(it to item)}
    private infix fun Silkable.drops(lootTable: Builder) = this.block.let{addDropWithSilkTouch(it);+(it to lootTable)}
    private infix fun Silkable.drops(lootTableSupplier: (Block)->Builder) = this.block.let{addDropWithSilkTouch(it);+(it to lootTableSupplier)}


    @JvmName("varargLeaves")
    private fun leaves(sapling: Block, vararg dropChance: Float): (Block)->Builder = { leavesDrop(it, sapling, *dropChance) }
    private fun leaves(sapling: Block, dropChance: FloatArray): (Block)->Builder = { leavesDrop(it, sapling, *dropChance) }

    private operator fun Block.unaryPlus() = addDrop(this)
    private operator fun Silkable.unaryPlus() = addDropWithSilkTouch(block)

    @JvmName("addItemLoot")
    operator fun Pair<Block, ItemConvertible>.unaryPlus() = addDrop(first, second)
    @JvmName("addLootTable")
    operator fun Pair<Block, Builder>.unaryPlus() = addDrop(first, second)
    @JvmName("addLootTableSupplier")
    operator fun Pair<Block, (Block)->Builder>.unaryPlus() = addDrop(first, Function(second))

    @JvmInline private value class Silkable(val block: Block)
    private val Block.silkable get() = Silkable(this)


    companion object{
        private val SAPLING_DROP_CHANCE = floatArrayOf(0.05f, 0.0625f, 0.083333336f, 0.1f)
        private object ITSELF
    }
}