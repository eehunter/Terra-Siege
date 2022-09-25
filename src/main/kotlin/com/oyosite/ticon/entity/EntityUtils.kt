package com.oyosite.ticon.entity

import net.minecraft.entity.ai.goal.Goal
import net.minecraft.entity.ai.pathing.PathNodeType
import net.minecraft.entity.mob.MobEntity
import net.minecraft.entity.mob.PathAwareEntity

fun PathAwareEntity.pathingPenalties(vararg penalties: Pair<PathNodeType, Number>) = penalties.forEach{setPathfindingPenalty(it.first, it.second.toFloat())}

fun AccessibleGoalSelector.addGoals(vararg goals: Pair<Int, Goal>) = goals.forEach { this.goalSelectorAccess.add(it.first,it.second) }