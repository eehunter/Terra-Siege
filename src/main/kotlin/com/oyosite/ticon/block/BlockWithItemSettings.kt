package com.oyosite.ticon.block

import net.fabricmc.fabric.api.item.v1.FabricItemSettings

interface BlockWithItemSettings {
    val itemSettings: FabricItemSettings
}