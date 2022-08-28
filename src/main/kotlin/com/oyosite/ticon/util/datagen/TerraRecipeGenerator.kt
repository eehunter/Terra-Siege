package com.oyosite.ticon.util.datagen

import com.oyosite.ticon.block.BlockRegistry
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.minecraft.data.server.recipe.RecipeJsonProvider
import java.util.function.Consumer

class TerraRecipeGenerator(dataGenerator: FabricDataGenerator) : FabricRecipeProvider(dataGenerator) {
    override fun generateRecipes(exporter: Consumer<RecipeJsonProvider>) = BlockRegistry.run{
        offerPlanksRecipe(exporter, TERRA_PLANKS, TERRA_LOGS)
    }
}