package com.oyosite.ticon.util.datagen

import com.oyosite.ticon.block.BlockRegistry
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.data.client.BlockStateModelGenerator
import net.minecraft.data.client.BlockStateModelGenerator.TintType.NOT_TINTED
import net.minecraft.data.client.ItemModelGenerator
import net.minecraft.data.client.TexturedModel.*

class TerraModelGenerator(dataGenerator:FabricDataGenerator) : FabricModelProvider(dataGenerator) {
    override fun generateBlockStateModels(bsmGen: BlockStateModelGenerator) = BlockRegistry.run{bsmGen.run{
        registerSimpleCubeAll(TERRATHIL_STONE)
        registerSimpleCubeAll(TERRATHIL_COBBLESTONE)

        registerSimpleCubeAll(TERRATHIL_COPPER_ORE)



        registerSimpleCubeAll(TERRA_LEAVES)
        registerAxisRotated(TERRA_LOG, CUBE_COLUMN)
        registerTintableCross(TERRA_SAPLING, NOT_TINTED)
        registerSimpleCubeAll(TERRA_PLANKS)
    }}

    override fun generateItemModels(itemModelGen: ItemModelGenerator) {
    }
}