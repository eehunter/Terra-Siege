@file:Suppress("UnstableApiUsage")

package com.oyosite.ticon.util.datagen

import com.oyosite.ticon.TerraSiege
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.fabricmc.fabric.impl.datagen.FabricDataGenHelper
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.tag.TagKey
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryKey
import net.minecraft.world.biome.Biome
import net.minecraft.world.biome.BiomeKeys


abstract class TerraTagProvider<T>(dataGenerator: FabricDataGenerator, registry: Registry<T>): FabricTagProvider<T>(dataGenerator, registry) {
    val TagKey<T>.builder: FabricTagBuilder<T> get() = getOrCreateTagBuilder(this)
    operator fun TagKey<T>.rem(element: T): FabricTagBuilder<T> = builder.add(element)
    operator fun TagKey<T>.rem(id: Identifier): FabricTagBuilder<T> = builder.add(id)
    operator fun FabricTagBuilder<T>.plus(element: T): FabricTagBuilder<T> = add(element)
    operator fun FabricTagBuilder<T>.plus(id: Identifier): FabricTagBuilder<T> = add(id)
}
abstract class ItemTagProvider(dataGenerator: FabricDataGenerator) : TerraTagProvider<Item>(dataGenerator, Registry.ITEM){
    operator fun TagKey<Item>.rem(element: Block): FabricTagBuilder<Item> = builder.add(element.asItem())
    operator fun FabricTagBuilder<Item>.plus(element: Block): FabricTagBuilder<Item> = add(element.asItem())
}
abstract class BlockTagProvider(dataGenerator: FabricDataGenerator) : TerraTagProvider<Block>(dataGenerator, Registry.BLOCK)
abstract class DynamicRegistryTagProvider<T>(dataGenerator: FabricDataGenerator, key: RegistryKey<Registry<T>>) : FabricTagProvider.DynamicRegistryTagProvider<T>(dataGenerator, key){
    val TagKey<T>.builder: FabricTagBuilder<T> get() = getOrCreateTagBuilder(this)
    operator fun TagKey<T>.rem(element: T): FabricTagBuilder<T> = builder.add(element)
    operator fun TagKey<T>.rem(id: Identifier): FabricTagBuilder<T> = builder.add(id)
    operator fun FabricTagBuilder<T>.plus(element: T): FabricTagBuilder<T> = add(element)
    operator fun FabricTagBuilder<T>.plus(id: Identifier): FabricTagBuilder<T> = add(id)
    operator fun TagKey<T>.rem(id: String): FabricTagBuilder<T> = this % TerraSiege.id(id)
    operator fun FabricTagBuilder<T>.plus(id: String): FabricTagBuilder<T> = this + TerraSiege.id(id)
}
abstract class BiomeTagProvider(dataGenerator: FabricDataGenerator) : DynamicRegistryTagProvider<Biome>(dataGenerator, Registry.BIOME_KEY)
