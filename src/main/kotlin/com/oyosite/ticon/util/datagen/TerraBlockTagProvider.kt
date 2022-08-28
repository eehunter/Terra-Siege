package com.oyosite.ticon.util.datagen

import com.oyosite.ticon.block.BlockRegistry
import com.oyosite.ticon.util.noReturn
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator

class TerraBlockTagProvider(dataGenerator: FabricDataGenerator) : BlockTagProvider(dataGenerator) {
    override fun generateTags() = BlockRegistry.run{
        TERRATHIL_STONE_ORE_REPLACEABLES % TERRATHIL_STONE





    }.noReturn
}