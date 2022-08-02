package com.oyosite.ticon

import com.oyosite.ticon.block.BlockRegistry
import com.oyosite.ticon.item.ItemRegistry
import com.oyosite.ticon.worldgen.Features
import net.fabricmc.api.ModInitializer
import net.minecraft.util.Identifier

object TerraSiege : ModInitializer {
    /**Terra Siege modid*/
    const val MODID = "terra_siege"
    /**Make an identifier for Terra Siege*/
    fun id(id: String) = (if(id.contains(":"))Identifier(id) else Identifier(MODID, id))

    /**Terra Siege common initializer function*/
    override fun onInitialize() {
        Features.registerAll
        BlockRegistry.registerAll
        ItemRegistry.registerAll
    }
}