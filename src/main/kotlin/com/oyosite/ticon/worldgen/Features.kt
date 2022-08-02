package com.oyosite.ticon.worldgen

import com.oyosite.ticon.TerraSiege
import com.oyosite.ticon.block.BlockRegistry
import com.oyosite.ticon.dimensions.TerrathilDimension
import net.fabricmc.fabric.api.biome.v1.BiomeModifications
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.minecraft.block.Blocks
import net.minecraft.tag.BiomeTags
import net.minecraft.util.math.intprovider.ConstantIntProvider
import net.minecraft.util.registry.BuiltinRegistries
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryEntry
import net.minecraft.util.registry.RegistryKey
import net.minecraft.world.Heightmap
import net.minecraft.world.gen.GenerationStep
import net.minecraft.world.gen.feature.*
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize
import net.minecraft.world.gen.foliage.BlobFoliagePlacer
import net.minecraft.world.gen.placementmodifier.HeightmapPlacementModifier
import net.minecraft.world.gen.placementmodifier.PlacementModifier
import net.minecraft.world.gen.placementmodifier.PlacementModifierType
import net.minecraft.world.gen.stateprovider.BlockStateProvider
import net.minecraft.world.gen.trunk.StraightTrunkPlacer
import kotlin.jvm.optionals.getOrNull

object Features {
    val registerAll: Unit get(){
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, terraTree.value, TERRA_TREE_FEATURE)
        Registry.register(BuiltinRegistries.PLACED_FEATURE, terraTree.value, PLACED_TERRA_TREE_FEATURE)
        BiomeModifications.addFeature({it.biomeRegistryEntry.isIn(TerrathilDimension.TERRA_FOREST_BIOMES)}, GenerationStep.Feature.VEGETAL_DECORATION, placedTerraTree)
    }


    val terraTree: RegistryKey<ConfiguredFeature<*, *>> = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, TerraSiege.id("terra_tree"))
    val placedTerraTree: RegistryKey<PlacedFeature> = RegistryKey.of(Registry.PLACED_FEATURE_KEY, TerraSiege.id("terra_tree"))

    @OptIn(ExperimentalStdlibApi::class)
    val terraTreeEntry get() = BuiltinRegistries.CONFIGURED_FEATURE.getEntry(terraTree).getOrNull()
    @OptIn(ExperimentalStdlibApi::class)
    val placedTerraTreeEntry get() = BuiltinRegistries.PLACED_FEATURE.getEntry(placedTerraTree).getOrNull()
    val TERRA_TREE_FEATURE = ConfiguredFeature(Feature.TREE,TreeFeatureConfig.Builder(
        BlockStateProvider.of(BlockRegistry.TERRA_LOG),
        StraightTrunkPlacer(4,2,0),
        BlockStateProvider.of(BlockRegistry.TERRA_LEAVES),
        BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 3),
        TwoLayersFeatureSize(1, 0, 1)
    ).build())
    val PLACED_TERRA_TREE_FEATURE by lazy { PlacedFeature(terraTreeEntry, mutableListOf<PlacementModifier>(HeightmapPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING), *VegetationPlacedFeatures.modifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(10, 0.1f, 1), Blocks.OAK_SAPLING).toTypedArray())) }
}