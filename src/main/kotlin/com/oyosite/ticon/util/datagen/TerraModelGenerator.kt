package com.oyosite.ticon.util.datagen

import com.oyosite.ticon.block.BlockRegistry
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.data.client.BlockStateModelGenerator
import net.minecraft.data.client.ItemModelGenerator

class TerraModelGenerator(dataGenerator:FabricDataGenerator) : FabricModelProvider(dataGenerator) {
    override fun generateBlockStateModels(bsModelGen: BlockStateModelGenerator) {
        bsModelGen.registerSimpleCubeAll(BlockRegistry.TERRATHIL_STONE)
    }

    override fun generateItemModels(itemModelGen: ItemModelGenerator) {
    }
}