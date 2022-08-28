package com.oyosite.ticon.util.datagen

import com.oyosite.ticon.block.BlockRegistry
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLootTableProvider
import net.minecraft.block.Block
import net.minecraft.item.ItemConvertible
import net.minecraft.item.Items.RAW_COPPER
import net.minecraft.loot.LootTable
import net.minecraft.loot.LootTable.Builder
import java.util.function.Function
import kotlin.reflect.KFunction

class TerraBlockLootTableGenerator(dataGenerator: FabricDataGenerator): FabricBlockLootTableProvider(dataGenerator) {
    override fun generateBlockLootTables() = BlockRegistry.run{
        addDrops(
            TERRATHIL_STONE.silkable, TERRATHIL_COBBLESTONE to TERRATHIL_STONE,
            TERRATHIL_COPPER_ORE.silkable, RAW_COPPER to TERRATHIL_COPPER_ORE,

            TERRA_LOG, TERRA_PLANKS,
            TERRA_LEAVES.silkable, //TODO: Terra leaves standard drops

        )

        /*addDropWithSilkTouch(TERRATHIL_STONE)
        addDrop(TERRATHIL_STONE, TERRATHIL_COBBLESTONE)

        addDropWithSilkTouch(TERRATHIL_COPPER_ORE)
        addDrop(TERRATHIL_COPPER_ORE, RAW_COPPER)


        addDrop(TERRA_LOG)*/


    }



    


    @JvmInline private value class Silkable(val block: Block)
    private val Block.silkable get() = Silkable(this)
    private fun addDrops(vararg blocks: Any) {
        for (block in blocks) when (block) {
            is Block -> addDrop(block)
            is Pair<*, *> -> addDrop(block)
            is Silkable -> addDropWithSilkTouch(block.block)
        }
    }
    private fun addDrop(data: Pair<*,*>){
        if (data.second !is Block) return if (data.second is Silkable) addDropWithSilkTouch((data.second as Silkable).block, data.first as Block) else Unit

        val block: Block = data.second as Block
        when(val drop: Any = data.first!!){
            is ItemConvertible -> addDrop(block, drop)
            is Builder -> addDrop(block, data.first as Builder)
            else -> addDrop(block, Function{ (data.first as (Block)->Builder?).invoke(it) } )
        }
    }
}