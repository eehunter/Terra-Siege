package com.oyosite.ticon.worldgen

import com.oyosite.ticon.TerraSiege
import net.minecraft.util.Identifier
import net.minecraft.util.registry.BuiltinRegistries
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryEntry
import net.minecraft.util.registry.RegistryKey
import net.minecraft.world.gen.feature.*
import kotlin.jvm.optionals.getOrNull

open class TerraFeatureHolder<C: FeatureConfig, F: Feature<C>>(val id: Identifier, val configured: ConfiguredFeature<C, F>, private val placedFeatureBuilder: TerraFeatureHolder<C, F>.()->PlacedFeature, val addToBiomes: TerraFeatureHolder<C, F>.() -> Unit = {}) {
    val placed: PlacedFeature by lazy{placedFeatureBuilder()}
    val configuredKey: RegistryKey<ConfiguredFeature<*, *>> = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, id)
    val placedKey: RegistryKey<PlacedFeature> = RegistryKey.of(Registry.PLACED_FEATURE_KEY, id)
    @OptIn(ExperimentalStdlibApi::class)
    val configuredEntry: RegistryEntry<ConfiguredFeature<*, *>>? get() = BuiltinRegistries.CONFIGURED_FEATURE.getEntry(configuredKey).getOrNull()
    @OptIn(ExperimentalStdlibApi::class)
    val placedEntry: RegistryEntry<PlacedFeature>? get() = BuiltinRegistries.PLACED_FEATURE.getEntry(placedKey).getOrNull()


    val register: Unit get(){
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, id, configured)
        Registry.register(BuiltinRegistries.PLACED_FEATURE, id, placed)
        addToBiomes()
    }

}