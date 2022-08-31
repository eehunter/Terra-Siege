package com.oyosite.ticon.dimensions

import com.oyosite.ticon.TerraSiege.MODID
import com.oyosite.ticon.TerraSiege.id
import net.minecraft.tag.TagKey
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryKey

object TerrathilDimension {
    val DIMENSION = RegistryKey.of(Registry.DIMENSION_KEY, Identifier(MODID, "terrathil"))

    val TERRATHIL_BIOMES = TagKey.of(Registry.BIOME_KEY,id("terrathil_biome"))
    val TERRA_FOREST_BIOMES = TagKey.of(Registry.BIOME_KEY,id("terra_siege:terra_forest"))

}