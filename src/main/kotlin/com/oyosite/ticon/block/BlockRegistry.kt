package com.oyosite.ticon.block

import com.oyosite.ticon.TerraSiege
import com.oyosite.ticon.item.ItemRegistry
import com.oyosite.ticon.util.registry.BasicRegistry
import com.oyosite.ticon.worldgen.Features
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.block.*
import net.minecraft.block.Blocks.COPPER_ORE
import net.minecraft.entity.EntityType
import net.minecraft.item.BlockItem
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.tag.TagKey
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.registry.Registry
import net.minecraft.world.BlockView

@Suppress("unused")
object BlockRegistry : BasicRegistry<Block>() {
    private val Block.itemSettings: FabricItemSettings get() = (if(this is BlockWithItemSettings) (this as BlockWithItemSettings).itemSettings else FabricItemSettings())
    override fun register(obj: Block, id: Identifier) { Registry.register(Registry.BLOCK, id, obj);Registry.register(Registry.ITEM, id, BlockItem(obj, obj.itemSettings)) }
    fun blockSettings(block: Block) = AbstractBlock.Settings.copy(block)
    fun blockSettings(material: Material) = AbstractBlock.Settings.of(material)
    fun blockSettings(material: Material, color: MapColor) = AbstractBlock.Settings.of(material, color)

    val TERRATHIL_STONE = "terrathil_stone" register Block(blockSettings(Material.STONE, MapColor.STONE_GRAY).strength(1.5f, 6f))
    val TERRATHIL_COBBLESTONE = "terrathil_cobblestone" register Block(blockSettings(Material.STONE, MapColor.STONE_GRAY).strength(1.5f, 6f))
    val TERRATHIL_STONE_ORE_REPLACEABLES: TagKey<Block> = TagKey.of(Registry.BLOCK_KEY, TerraSiege.id("terrathil_stone_ore_replaceables"))
    // Ores for terrathil dimension
    val TERRATHIL_COPPER_ORE = "terrathil_copper_ore" register OreBlock(blockSettings(COPPER_ORE))


    // Tree stuff
    val TERRA_LOG = "terra_log" register logBlock(MapColor.OAK_TAN, MapColor.SPRUCE_BROWN)
    val TERRA_PLANKS: Block = "terra_planks" register object : Block(blockSettings(Material.WOOD, MapColor.OAK_TAN)), BlockWithItemSettings{
        override val itemSettings: FabricItemSettings = FabricItemSettings().group(ItemRegistry.TERRATHIL_ITEM_GROUP)
    }
    val TERRA_LEAVES = "terra_leaves" register LeavesBlock(blockSettings(Material.LEAVES).strength(0.2f).ticksRandomly().sounds(BlockSoundGroup.GRASS).nonOpaque().allowsSpawning { _: BlockState, _: BlockView, _: BlockPos, _: EntityType<*> -> false }.suffocates { _: BlockState, _: BlockView, _: BlockPos -> false }.blockVision { _: BlockState, _: BlockView, _: BlockPos -> false })
    val TERRA_SAPLING: Block = "terra_sapling" register TerraSiegeSapling{Features.TERRA_TREE.configuredEntry!!}




    private fun logBlock(topMapColor: MapColor, sideMapColor: MapColor) = PillarBlock(AbstractBlock.Settings.of(Material.WOOD) { state: BlockState -> if (state.get(PillarBlock.AXIS) === Direction.Axis.Y) topMapColor else sideMapColor }.strength(2.0f).sounds(BlockSoundGroup.WOOD))
}