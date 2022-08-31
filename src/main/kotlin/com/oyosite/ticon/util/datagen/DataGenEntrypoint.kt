package com.oyosite.ticon.util.datagen

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider

object DataGenEntrypoint : DataGeneratorEntrypoint {
    override fun onInitializeDataGenerator(dataGen: FabricDataGenerator) {
        println("Hello world from datagen")
        println("Output path: "+dataGen.output)
        dataGen.addProvider(::TerraModelGenerator)
        dataGen.addProvider(::TerraBlockLootTableGenerator)
        dataGen.addProvider(::TerraRecipeGenerator)
        listOf<(FabricDataGenerator)->FabricTagProvider<*>>(
            ::TerraBlockTagProvider, ::TerraItemTagProvider, ::TerraBiomeTagProvider
        ).forEach{dataGen.addProvider(it)}
    }
}