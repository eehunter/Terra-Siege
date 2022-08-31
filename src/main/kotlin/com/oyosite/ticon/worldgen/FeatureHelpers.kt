package com.oyosite.ticon.worldgen

import com.oyosite.ticon.TerraSiege
import com.oyosite.ticon.dimensions.TerrathilDimension
import com.oyosite.ticon.worldgen.Features.TERRATHIL_STONE_ORE_REPLACEABLES
import com.oyosite.ticon.worldgen.Features.unaryPlus
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.structure.rule.RuleTest
import net.minecraft.tag.TagKey
import net.minecraft.world.biome.Biome
import net.minecraft.world.gen.YOffset
import net.minecraft.world.gen.feature.*
import net.minecraft.world.gen.placementmodifier.CountPlacementModifier
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier
import net.minecraft.world.gen.placementmodifier.PlacementModifier
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier


private fun defaultOrePlacementModifiers(count: Int, minY: Int, maxY: Int) = arrayOf(CountPlacementModifier.of(count), SquarePlacementModifier.of(), HeightRangePlacementModifier.trapezoid(YOffset.fixed(minY), YOffset.fixed(maxY)))

fun terraOre(
    id: String, ruleTest: RuleTest = TERRATHIL_STONE_ORE_REPLACEABLES, blockState: BlockState,
    size: Int, count: Int, minY: Int, maxY: Int,
    biomeTag: TagKey<Biome> = TerrathilDimension.TERRATHIL_BIOMES,
    addToBiomes: TerraFeatureHolder<OreFeatureConfig, Feature<OreFeatureConfig>>.()->Unit = Features.oreBiomeMod { it.biomeRegistryEntry.isIn(biomeTag) },
    vararg placementModifiers: PlacementModifier = defaultOrePlacementModifiers(count, minY, maxY)
): TerraFeatureHolder<OreFeatureConfig, Feature<OreFeatureConfig>> = +TerraFeatureHolder<OreFeatureConfig, Feature<OreFeatureConfig>>(TerraSiege.id(id), ConfiguredFeature(Feature.ORE, OreFeatureConfig(ruleTest, blockState,size)), { PlacedFeature(configuredEntry, mutableListOf(*placementModifiers)) }, addToBiomes)
fun terraOre(id: String, block: Block, size: Int, count: Int, minY: Int, maxY: Int, biomeTag: TagKey<Biome> = TerrathilDimension.TERRATHIL_BIOMES, addToBiomes: TerraFeatureHolder<OreFeatureConfig, Feature<OreFeatureConfig>>.()->Unit = Features.oreBiomeMod {
    it.biomeRegistryEntry.isIn(biomeTag)
}, ruleTest: RuleTest = TERRATHIL_STONE_ORE_REPLACEABLES, vararg placementModifiers: PlacementModifier = defaultOrePlacementModifiers(count, minY, maxY)
): TerraFeatureHolder<OreFeatureConfig, Feature<OreFeatureConfig>> =
    terraOre(id, ruleTest, block.defaultState, size, count, minY, maxY, biomeTag, addToBiomes, *placementModifiers)