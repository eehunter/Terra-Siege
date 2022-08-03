package com.oyosite.ticon.util

import net.minecraft.util.math.Vec3i

object VectorMath {


    operator fun Vec3i.plus(other: Vec3i): Vec3i = this.add(other)
}