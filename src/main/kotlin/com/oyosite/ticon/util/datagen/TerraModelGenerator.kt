package com.oyosite.ticon.util.datagen

import com.oyosite.ticon.block.BlockRegistry
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.block.Block
import net.minecraft.data.client.BlockStateModelGenerator
import net.minecraft.data.client.BlockStateModelGenerator.TintType.NOT_TINTED
import net.minecraft.data.client.ItemModelGenerator
import net.minecraft.data.client.TexturedModel.*

class TerraModelGenerator(dataGenerator:FabricDataGenerator) : FabricModelProvider(dataGenerator) {
    override fun generateBlockStateModels(bsmGen: BlockStateModelGenerator) = BlockRegistry.run{bsmGen.run{
        val ores = arrayOf(TERRATHIL_COPPER_ORE)


        cube(TERRATHIL_STONE, TERRATHIL_COBBLESTONE, *ores)


        cube(TERRA_LEAVES, TERRA_PLANKS)
        registerAxisRotated(TERRA_LOG, CUBE_COLUMN)
        registerTintableCross(TERRA_SAPLING, NOT_TINTED)
    }}

    override fun generateItemModels(itemModelGen: ItemModelGenerator) {
    }

    private fun BlockStateModelGenerator.cube(vararg blocks: Block) = blocks.forEach(this::registerSimpleCubeAll)
}