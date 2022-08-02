package com.oyosite.ticon.util.registry

import com.oyosite.ticon.TerraSiege
import net.minecraft.util.Identifier

abstract class BasicRegistry<T> {
    protected val OBJECTS = mutableListOf<Pair<Identifier,T>>()
    val registerAll: Unit get() = OBJECTS.forEach{(id, obj)->register(obj, id)}
    abstract fun register(obj: T, id: Identifier)
    protected infix fun <U : T> String.register(obj: U): U = obj.also{ OBJECTS.add(TerraSiege.id(this) to it)}
}