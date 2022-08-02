package com.oyosite.ticon.block

import net.minecraft.block.Blocks
import net.minecraft.block.SaplingBlock
import net.minecraft.block.sapling.SaplingGenerator
import net.minecraft.util.math.random.Random
import net.minecraft.util.registry.RegistryEntry
import net.minecraft.world.gen.feature.ConfiguredFeature

class TerraSiegeSapling(generator : SaplingGenerator, settings: Settings = Settings.copy(Blocks.OAK_SAPLING)) : SaplingBlock(generator, settings){
    constructor(settings: Settings = Settings.copy(Blocks.OAK_SAPLING), feature : ()->RegistryEntry<out ConfiguredFeature<*,*>>?) : this(TerraSiegeSaplingGenerator(feature), settings)

    class TerraSiegeSaplingGenerator(val feature : ()->RegistryEntry<out ConfiguredFeature<*,*>>?) : SaplingGenerator(){

        override fun getTreeFeature(random: Random?, bees: Boolean): RegistryEntry<out ConfiguredFeature<*, *>>? = feature()

    }

}