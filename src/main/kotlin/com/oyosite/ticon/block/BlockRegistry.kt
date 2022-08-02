package com.oyosite.ticon.block

import com.oyosite.ticon.util.registry.BasicRegistry
import com.oyosite.ticon.worldgen.Features
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.block.*
import net.minecraft.entity.EntityType
import net.minecraft.item.BlockItem
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.registry.Registry
import net.minecraft.world.BlockView

@Suppress("unused")
object BlockRegistry : BasicRegistry<Block>() {
    private val Block.itemSettings: FabricItemSettings get() = (if(this is BlockWithItemSettings) (this as BlockWithItemSettings).itemSettings else FabricItemSettings())
    override fun register(obj: Block, id: Identifier) { Registry.register(Registry.BLOCK, id, obj);Registry.register(Registry.ITEM, id, BlockItem(obj, obj.itemSettings)) }

    val TERRATHIL_STONE = "terrathil_stone" register Block(AbstractBlock.Settings.of(Material.STONE, MapColor.STONE_GRAY).strength(1.5f, 6f))
    val TERRA_LOG = "terra_log" register logBlock(MapColor.OAK_TAN, MapColor.SPRUCE_BROWN)
    val TERRA_LEAVES = "terra_leaves" register LeavesBlock(AbstractBlock.Settings.of(Material.LEAVES).strength(0.2f).ticksRandomly().sounds(BlockSoundGroup.GRASS).nonOpaque().allowsSpawning { _: BlockState, _: BlockView, _: BlockPos, _: EntityType<*> -> false }.suffocates { _: BlockState, _: BlockView, _: BlockPos -> false }.blockVision { _: BlockState, _: BlockView, _: BlockPos -> false })
    val TERRA_SAPLING = "terra_sapling" register TerraSiegeSapling{Features.terraTreeEntry}




    private fun logBlock(topMapColor: MapColor, sideMapColor: MapColor) = PillarBlock(AbstractBlock.Settings.of(Material.WOOD) { state: BlockState -> if (state.get(PillarBlock.AXIS) === Direction.Axis.Y) topMapColor else sideMapColor }.strength(2.0f).sounds(BlockSoundGroup.WOOD))
}