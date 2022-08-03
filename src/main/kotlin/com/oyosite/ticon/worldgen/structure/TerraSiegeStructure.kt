package com.oyosite.ticon.worldgen.structure

import com.oyosite.ticon.TerraSiege
import net.minecraft.util.Identifier
import net.minecraft.util.registry.BuiltinRegistries
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryKey
import net.minecraft.world.gen.structure.Structure

abstract class TerraSiegeStructure(val id: Identifier, config: Config): Structure(config) {
    constructor(id: String, config: Config) : this(TerraSiege.id(id), config)

    val KEY = RegistryKey.of(Registry.STRUCTURE_KEY, id)
    val register get() = Registry.register(BuiltinRegistries.STRUCTURE, KEY, this)


}