package com.oyosite.ticon

import com.oyosite.ticon.block.BlockRegistry
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry
import net.minecraft.client.color.world.BiomeColors
import net.minecraft.client.color.world.FoliageColors


object TerraSiegeClient : ClientModInitializer{
    override fun onInitializeClient() {
        ColorProviderRegistry.BLOCK.register({ state, view, pos, tintIndex -> if (view == null || pos == null) FoliageColors.getDefaultColor() else BiomeColors.getFoliageColor(view, pos) }, BlockRegistry.TERRA_LEAVES)
        ColorProviderRegistry.ITEM.register({ stack, tintIndex -> FoliageColors.getDefaultColor() }, BlockRegistry.TERRA_LEAVES.asItem())
    }
}