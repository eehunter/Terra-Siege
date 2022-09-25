package com.oyosite.ticon.util.datagen

import com.oyosite.ticon.TerraSiege.id
import com.oyosite.ticon.dimensions.TerrathilDimension
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator

class TerraBiomeTagProvider(dataGenerator: FabricDataGenerator) : BiomeTagProvider(dataGenerator) {
    override fun generateTags() {
        TerrathilDimension.TERRATHIL_BIOMES % "terra_plains" + "terra_forest"
        TerrathilDimension.TERRA_FOREST_BIOMES % "terra_forest"
    }
}