package com.oyosite.ticon.worldgen.structure

import com.oyosite.ticon.TerraSiege
import net.minecraft.structure.StructurePiecesCollector
import net.minecraft.util.BlockRotation
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.world.gen.structure.StructureType
import java.util.*
import com.oyosite.ticon.worldgen.structure.CastleGenerator.Tile
import com.oyosite.ticon.worldgen.structure.CastleGenerator.Piece
import com.oyosite.ticon.worldgen.structure.StructureRegistry.CASTLE_SPAWNABLE_BIOME
import net.minecraft.util.BlockMirror
import net.minecraft.util.registry.BuiltinRegistries
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryKey
import net.minecraft.world.gen.GenerationStep
import net.minecraft.world.gen.StructureTerrainAdaptation

class CastleStructure(config: Config): TerraSiegeStructure(TerraSiege.id("castle"), config) {
    constructor(): this(Config(BuiltinRegistries.BIOME.getOrCreateEntryList(CASTLE_SPAWNABLE_BIOME), mapOf(), GenerationStep.Feature.SURFACE_STRUCTURES, StructureTerrainAdaptation.NONE))

    val generator = CastleGenerator()

    override fun getStructurePosition(context: Context): Optional<StructurePosition> {
        val blockRotation = BlockRotation.random(context.random())
        val blockPos = getShiftedPos(context, blockRotation)//context.chunkPos.startPos//
        return /*if (blockPos.y < 60) Optional.empty()
        else */Optional.of(StructurePosition(blockPos) { collector: StructurePiecesCollector ->
            this.addPieces(collector, context, blockPos, blockRotation)
        })
    }

    private fun addPieces(collector: StructurePiecesCollector, context: Context, pos: BlockPos, rotation: BlockRotation) {
        val layout = mutableMapOf<Pair<Int, Int>,Tile>()
        val list = mutableListOf<Piece>()
        generator.generateLayout(pos, rotation, layout)
        TerraSiege.LOGGER.info(layout.entries.filter { it.value.template!=null }.run{"$this\n${this.size}"})
        //Exception("This is a fake error").printStackTrace()
        //try{
        for(e in layout.entries.filter { it.value.template!=null }){
            println(e)
            try {
                list.add(getStructurePiece(context, pos.add(e.key.first * 16, 0, e.key.second * 16), rotation, e.value))
            }catch (x:Exception){
                TerraSiege.LOGGER.error("There is in fact an error here")
                x.printStackTrace()

            }
        }
        //list.addAll(layout.entries.filter { it.value.template != null }.map { (k, v) -> getStructurePiece(context, pos.add(k.first * 16, 0, k.second * 16), rotation, v) })
        TerraSiege.LOGGER.info(list)
        //}catch (e:Exception) {TerraSiege.LOGGER.error(e.stackTrace)}
        list.forEach(collector::addPiece)
    }

    private fun getStructurePiece(context: Context, pos: BlockPos, rotation: BlockRotation, tile: Tile):Piece{
        println("pos: $pos template: ${tile.template} rotation: $rotation")
        return Piece(context.structureTemplateManager, tile.template!!, pos, BlockMirror.NONE, rotation.rotate(tile.rotation))
    }

    override fun getType(): StructureType<*> = CASTLE

    companion object{
        val CODEC = createCodec(::CastleStructure)
        val CASTLE = Registry.register(Registry.STRUCTURE_TYPE, TerraSiege.id("castle"), StructureType{ CODEC })
        val KEY = RegistryKey.of(Registry.STRUCTURE_KEY, TerraSiege.id("castle"))


    }
}