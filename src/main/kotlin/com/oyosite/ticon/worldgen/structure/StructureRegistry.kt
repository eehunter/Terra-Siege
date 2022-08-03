package com.oyosite.ticon.worldgen.structure

import com.oyosite.ticon.TerraSiege
import net.minecraft.tag.TagKey
import net.minecraft.util.registry.BuiltinRegistries
import net.minecraft.util.registry.Registry
import net.minecraft.world.biome.Biome

object StructureRegistry {
    val CASTLE_SPAWNABLE_BIOME: TagKey<Biome> = TagKey.of(Registry.BIOME_KEY, TerraSiege.id("castle_spawnable"))

    val CASTLE: CastleStructure = Registry.register(BuiltinRegistries.STRUCTURE, CastleStructure.KEY, CastleStructure())
}