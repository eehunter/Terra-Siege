package com.oyosite.ticon.dimensions

import com.oyosite.ticon.TerraSiege
import net.minecraft.tag.TagKey
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryKey

object TerrathilDimension {
    val DIMENSION = RegistryKey.of(Registry.DIMENSION_KEY, Identifier(TerraSiege.MODID, "terrathil"))

    val TERRA_FOREST_BIOMES = TagKey.of(Registry.BIOME_KEY,TerraSiege.id("terra_siege:terra_forest"))

}