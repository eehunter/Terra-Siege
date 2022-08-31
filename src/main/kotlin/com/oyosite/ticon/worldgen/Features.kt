package com.oyosite.ticon.worldgen

import com.oyosite.ticon.TerraSiege.id
import com.oyosite.ticon.block.BlockRegistry
import com.oyosite.ticon.block.BlockRegistry.TERRATHIL_COPPER_ORE
import com.oyosite.ticon.block.BlockRegistry.TERRA_SAPLING
import com.oyosite.ticon.dimensions.TerrathilDimension
import com.oyosite.ticon.dimensions.TerrathilDimension.TERRATHIL_BIOMES
import net.fabricmc.fabric.api.biome.v1.BiomeModifications
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext
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
    private operator fun TerraFeatureHolder<*,*>.unaryPlus() = also(allFeatures::add)
    val registerAll: Unit get() = allFeatures.forEach(TerraFeatureHolder<*,*>::register.getter)
    val TERRATHIL_STONE_ORE_REPLACEABLES = TagMatchRuleTest(BlockRegistry.TERRATHIL_STONE_ORE_REPLACEABLES)
    fun oreBiomeMod(biomeSelector: (BiomeSelectionContext)->Boolean): TerraFeatureHolder<*,*>.()->Unit = { BiomeModifications.addFeature(biomeSelector, GenerationStep.Feature.UNDERGROUND_ORES, placedKey) }
    fun terra_ore(
        id: String, ruleTest: RuleTest = TERRATHIL_STONE_ORE_REPLACEABLES, blockState: BlockState,
        size: Int, count: Int, minY: Int, maxY: Int,
        vararg placementModifiers: PlacementModifier = arrayOf(CountPlacementModifier.of(count),SquarePlacementModifier.of(),HeightRangePlacementModifier.trapezoid(YOffset.fixed(minY), YOffset.fixed(maxY))),
        biomeTag: TagKey<Biome> = TERRATHIL_BIOMES,
        addToBiomes: TerraFeatureHolder<OreFeatureConfig, Feature<OreFeatureConfig>>.()->Unit = oreBiomeMod { it.biomeRegistryEntry.isIn(biomeTag) }
    ) = +TerraFeatureHolder(id(id), ConfiguredFeature(Feature.ORE, OreFeatureConfig(ruleTest, blockState,size)), { PlacedFeature(configuredEntry, mutableListOf(*placementModifiers)) }, addToBiomes)

    val TERRA_COPPER_ORE = +TerraFeatureHolder(
        id("terra_copper_ore"),
        ConfiguredFeature(Feature.ORE, OreFeatureConfig(TERRATHIL_STONE_ORE_REPLACEABLES, TERRATHIL_COPPER_ORE.defaultState,9)),
        { PlacedFeature(configuredEntry, mutableListOf<PlacementModifier>(CountPlacementModifier.of(20),SquarePlacementModifier.of(), HeightRangePlacementModifier.trapezoid(YOffset.fixed(-16), YOffset.fixed(112)))) }
    ) {
        BiomeModifications.addFeature(
            { it.biomeRegistryEntry.isIn(TERRATHIL_BIOMES) },
            GenerationStep.Feature.UNDERGROUND_ORES,
            placedKey
        )
    }

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