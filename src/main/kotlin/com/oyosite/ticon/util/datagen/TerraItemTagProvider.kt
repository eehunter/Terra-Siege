package com.oyosite.ticon.util.datagen

import com.oyosite.ticon.block.BlockRegistry.TERRATHIL_COPPER_ORE
import com.oyosite.ticon.block.BlockRegistry.TERRA_LOG
import com.oyosite.ticon.item.ItemRegistry
import com.oyosite.ticon.util.noReturn
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator


class TerraItemTagProvider(dataGenerator: FabricDataGenerator) : ItemTagProvider(dataGenerator) {
    override fun generateTags() = ItemRegistry.run{
        TERRA_LOGS % TERRA_LOG
        C_COPPER_ORES % TERRATHIL_COPPER_ORE


    }.noReturn

}