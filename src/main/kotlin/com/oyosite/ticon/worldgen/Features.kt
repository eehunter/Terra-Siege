package com.oyosite.ticon.worldgen

import com.oyosite.ticon.TerraSiege.id
import com.oyosite.ticon.block.BlockRegistry
import com.oyosite.ticon.block.BlockRegistry.TERRATHIL_COPPER_ORE
import com.oyosite.ticon.block.BlockRegistry.TERRATHIL_IRON_ORE
import com.oyosite.ticon.block.BlockRegistry.TERRA_SAPLING
import com.oyosite.ticon.dimensions.TerrathilDimension
import com.oyosite.ticon.dimensions.TerrathilDimension.TERRATHIL_BIOMES
import net.fabricmc.fabric.api.biome.v1.BiomeModifications
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.structure.rule.RuleTest
import net.minecraft.structure.rule.TagMatchRuleTest
import net.minecraft.tag.TagKey
import net.minecraft.util.math.intprovider.ConstantIntProvider
import net.minecraft.world.Heightmap
import net.minecraft.world.biome.Biome
import net.minecraft.world.gen.GenerationStep
import net.minecraft.world.gen.YOffset
import net.minecraft.world.gen.feature.*
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize
import net.minecraft.world.gen.foliage.BlobFoliagePlacer
import net.minecraft.world.gen.placementmodifier.*
import net.minecraft.world.gen.stateprovider.BlockStateProvider
import net.minecraft.world.gen.trunk.StraightTrunkPlacer

@Suppress("MemberVisibilityCanBePrivate")
object Features {
    private val allFeatures = mutableListOf<TerraFeatureHolder<*,*>>()
    operator fun <C: FeatureConfig, F: Feature<C>> TerraFeatureHolder<C,F>.unaryPlus() = also(allFeatures::add)
    val registerAll: Unit get() = allFeatures.forEach(TerraFeatureHolder<*,*>::register.getter)
    val TERRATHIL_STONE_ORE_REPLACEABLES = TagMatchRuleTest(BlockRegistry.TERRATHIL_STONE_ORE_REPLACEABLES)
    // IDK Why, but BiomeModifications.addFeature doesn't seem to work correctly. For now, features must be referenced in biome JSON to behave correctly.
    fun oreBiomeMod(biomeSelector: (BiomeSelectionContext)->Boolean): TerraFeatureHolder<*,*>.()->Unit = { BiomeModifications.addFeature(biomeSelector, GenerationStep.Feature.UNDERGROUND_ORES, placedKey) }


    val TERRA_COPPER_ORE = terraOre("terra_copper_ore", TERRATHIL_COPPER_ORE, 9, 20, -16, 112)
    val TERRA_IRON_ORE = terraOre("terra_iron_ore", TERRATHIL_IRON_ORE, 9, 20, -16, 112)


    val TERRA_TREE = +TerraFeatureHolder<TreeFeatureConfig, Feature<TreeFeatureConfig>>(
        id("terra_tree"),
        ConfiguredFeature(Feature.TREE,TreeFeatureConfig.Builder(
            BlockStateProvider.of(BlockRegistry.TERRA_LOG),
            StraightTrunkPlacer(4,2,0),
            BlockStateProvider.of(BlockRegistry.TERRA_LEAVES),
            BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3),
            TwoLayersFeatureSize(1, 0, 1)
        ).build()),
        { PlacedFeature(configuredEntry, mutableListOf<PlacementModifier>(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING), *VegetationPlacedFeatures.modifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(10, 0.1f, 1), TERRA_SAPLING).toTypedArray())) }
    ) {
        BiomeModifications.addFeature(
            { it.biomeRegistryEntry.isIn(TerrathilDimension.TERRA_FOREST_BIOMES) },
            GenerationStep.Feature.VEGETAL_DECORATION,
            placedKey
        )
    }


}