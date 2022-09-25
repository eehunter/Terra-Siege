package com.oyosite.ticon.entity

import net.minecraft.entity.ai.goal.GoalSelector

interface AccessibleGoalSelector {
    val goalSelectorAccess: GoalSelector
}