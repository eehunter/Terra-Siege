package com.oyosite.ticon.item

import com.oyosite.ticon.TerraSiege
import com.oyosite.ticon.TerraSiege.id
import com.oyosite.ticon.block.BlockRegistry
import com.oyosite.ticon.util.registry.SimpleRegistry
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.tag.TagKey
import net.minecraft.util.registry.Registry

@Suppress("UNUSED")
object ItemRegistry : SimpleRegistry<Item>(Registry.ITEM) {
    val TERRATHIL_ITEM_GROUP: ItemGroup = FabricItemGroupBuilder.create(id("terrathil")).icon { ItemStack(BlockRegistry.TERRATHIL_STONE) }.appendItems { stacks ->
        BlockRegistry.run{ listOf(TERRATHIL_STONE, TERRATHIL_COBBLESTONE, TERRATHIL_COPPER_ORE, TERRA_LOG, TERRA_PLANKS, TERRA_LEAVES, TERRA_SAPLING) }.map{ItemStack(it)}.forEach(stacks::add)
    }.build()


    val TERRA_LOGS: TagKey<Item> = TagKey.of(Registry.ITEM_KEY, id("terra_logs"))
    val C_COPPER_ORES: TagKey<Item> = TagKey.of(Registry.ITEM_KEY, TerraSiege.cid("copper_ores"))
}