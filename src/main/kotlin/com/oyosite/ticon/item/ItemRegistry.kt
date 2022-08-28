package com.oyosite.ticon.item

import com.oyosite.ticon.TerraSiege
import com.oyosite.ticon.util.registry.SimpleRegistry
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.tag.TagKey
import net.minecraft.util.registry.Registry

@Suppress("UNUSED")
object ItemRegistry : SimpleRegistry<Item>(Registry.ITEM) {



    val TERRA_LOGS: TagKey<Item> = TagKey.of(Registry.ITEM_KEY, TerraSiege.id("terra_logs"))
    val C_COPPER_ORES: TagKey<Item> = TagKey.of(Registry.ITEM_KEY, TerraSiege.cid("copper_ores"))
}