package com.oyosite.ticon.worldgen.structure

import com.oyosite.ticon.TerraSiege
import com.oyosite.ticon.util.VectorMath.plus
import com.oyosite.ticon.worldgen.structure.CastleGenerator.Tile.*
import net.minecraft.nbt.NbtCompound
import net.minecraft.structure.*
import net.minecraft.util.BlockMirror
import net.minecraft.util.BlockRotation
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockBox
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Direction.*
import net.minecraft.util.math.random.Random
import net.minecraft.util.registry.Registry
import net.minecraft.world.ServerWorldAccess
import java.util.function.Function

class CastleGenerator {

    private var random: Random = Random.create(0x500L)

    fun generateLayout(pos: BlockPos, rotation: BlockRotation, layout: MutableMap<Pair<Int, Int>, Tile>){
        layout[0 to 0] = KEEP
        val s = mutableSetOf<Pair<Int, Int>>()
        for(i in 0..3){
            s.clear()
            layout.keys.forEach{ p ->
                nesw.forEach { if(random.nextBoolean()) s += p.first+it.x to p.second+it.z }
            }
            s.forEach { layout[it] = KEEP }
        }
        s.clear()
        layout.keys.forEach { p -> eightDirs.forEach { s += p.first+it.x to p.second+it.z } }
        s.forEach { if(!layout.containsKey(it)) layout[it] = COURTYARD }
        s.clear()
        layout.entries.filter { it.value == COURTYARD }.map{it.key}.forEach { p -> nesw.forEach { s += p.first+it.x to p.second+it.z } }
        s.forEach { if(!layout.containsKey(it)) layout[it] = COURTYARD }
        s.clear()
        layout.entries.filter { it.value == COURTYARD }.map{it.key}.forEach { p -> eightDirs.forEach { s += p.first+it.x to p.second+it.z } }
        s.forEach { if(!layout.containsKey(it)) layout[it] = WALL }

        val minX = layout.keys.minOfOrNull(Pair<Int, Int>::first)?:0
        val maxX = layout.keys.maxOfOrNull(Pair<Int, Int>::first)?:0
        val minZ = layout.keys.minOfOrNull(Pair<Int, Int>::second)?:0
        val maxZ = layout.keys.maxOfOrNull(Pair<Int, Int>::second)?:0

        //make walls directional
        val m = mutableMapOf<Pair<Int,Int>, Tile>()
        layout.filter { it.value == WALL }.forEach{ (k, t) -> m[k] = t }
        m.forEach { (p,_) ->
            val (x,z) = p
            layout[p] = handleDirection(m[x to z-1]==WALL, m[x+1 to z]==WALL, m[x to z+1]==WALL, m[x-1 to z]==WALL)
        }


        val sb = StringBuilder()
        for(z in minZ..maxZ){
            for(x in minX..maxX) sb.append(layout[x to z]?.char?:'X')
            sb.append('\n')
        }
        //TerraSiege.LOGGER.info(sb.toString())

    }

    private fun handleDirection(n: Boolean, e: Boolean, s: Boolean, w: Boolean): Tile{
        return if(n){
            if(s) WALL_NS
            else if(e) WALL_NE
            else WALL_NW
        } else if(s){
            if(e) WALL_SE else WALL_SW
        } else WALL_EW
    }

    class Piece: SimpleStructurePiece{
        constructor(manager: StructureTemplateManager, template: String, pos: BlockPos, mirror: BlockMirror, rotation: BlockRotation): super(TYPE,0,manager,TerraSiege.id("castle/$template"),template,placementData(rotation,mirror),pos)
        constructor(manager: StructureTemplateManager, nbt: NbtCompound): super(TYPE, nbt, manager, Function{ id -> placementData( BlockRotation.valueOf(nbt.getString("Rot")),BlockMirror.valueOf(nbt.getString("Mi"))) })

        override fun handleMetadata(metadata: String, pos: BlockPos, world: ServerWorldAccess, random: Random, boundingBox: BlockBox) {

        }
        override fun writeNbt(context: StructureContext, nbt: NbtCompound) {
            super.writeNbt(context, nbt)
            nbt.putString("Rot", placementData.rotation.name)
            nbt.putString("Mi", placementData.mirror.name)
        }

        override fun getId(): Identifier = TerraSiege.id("castle/$templateIdString")

        companion object{
            fun placementData(rotation:BlockRotation,mirror: BlockMirror) = StructurePlacementData().setRotation(rotation).setMirror(mirror)
            val TYPE: StructurePieceType = Registry.register(Registry.STRUCTURE_PIECE, TerraSiege.id("ctl"), StructurePieceType{ m:StructureContext, nbt:NbtCompound -> Piece(m.structureTemplateManager,nbt)})
        }
    }

    enum class Tile(val char: Char, val template: String? = null, val rotation: BlockRotation = BlockRotation.NONE){
        KEEP('K'), COURTYARD('C'), WALL('W'),
        WALL_NS('\u2551', "castle_wall_straight", BlockRotation.CLOCKWISE_90), WALL_EW('\u2550',"castle_wall_straight"),
        WALL_NE('\u255a', "castle_wall_corner", BlockRotation.CLOCKWISE_180), WALL_NW('\u255d', "castle_wall_corner", BlockRotation.CLOCKWISE_90),
        WALL_SE('\u2554', "castle_wall_corner", BlockRotation.COUNTERCLOCKWISE_90), WALL_SW('\u2557', "castle_wall_corner")

    }
    companion object{
        private val nesw = listOf(NORTH, EAST, SOUTH, WEST).map(Direction::getVector)
        private val eightDirs = nesw.let { listOf(it[0], it[0]+it[1], it[1], it[1]+it[2], it[2], it[2]+it[3], it[3], it[3]+it[0]) }
    }
}