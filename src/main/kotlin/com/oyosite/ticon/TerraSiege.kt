package com.oyosite.ticon

import com.oyosite.ticon.block.BlockRegistry
import com.oyosite.ticon.item.ItemRegistry
import com.oyosite.ticon.worldgen.Features
import com.oyosite.ticon.worldgen.structure.CastleGenerator
import com.oyosite.ticon.worldgen.structure.StructureRegistry
import net.fabricmc.api.ModInitializer
import net.minecraft.util.BlockRotation
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

object TerraSiege : ModInitializer {
    /**Terra Siege modid*/
    const val MODID = "terra_siege"
    /**Make an identifier for Terra Siege*/
    fun id(id: String) = (if(id.contains(":"))Identifier(id) else Identifier(MODID, id))
    /**Make an identifier for a common tag*/
    fun cid(id: String) = (if(id.contains(":"))Identifier(id) else Identifier("c", id))
    /**Terra Siege logger*/
    val LOGGER: Logger = LogManager.getLogger()

    /**Terra Siege common initializer function*/
    override fun onInitialize() {
        Features.registerAll
        BlockRegistry.registerAll
        ItemRegistry.registerAll
        StructureRegistry
        //CastleGenerator().generateLayout(BlockPos(0,0,0), BlockRotation.NONE, mutableMapOf())
    }
}