package com.oyosite.ticon.worldgen

import com.oyosite.ticon.TerraSiege
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryKey
import net.minecraft.world.gen.feature.ConfiguredFeature
import net.minecraft.world.gen.feature.PlacedFeature

class TerraSiegeTreeFeature {
    val treeKey: RegistryKey<ConfiguredFeature<*, *>> = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, TerraSiege.id("terra_tree"))
    val placedTreeKey: RegistryKey<PlacedFeature> = RegistryKey.of(Registry.PLACED_FEATURE_KEY, TerraSiege.id("terra_tree"))
}