package com.oyosite.ticon.util.registry

import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

open class SimpleRegistry<T>(val registry: Registry<T>) : BasicRegistry<T>() {
    override fun register(obj: T, id: Identifier) {Registry.register(registry, id, obj)}
}