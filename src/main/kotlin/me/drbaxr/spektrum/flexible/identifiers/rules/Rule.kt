package me.drbaxr.spektrum.flexible.identifiers.rules

import me.drbaxr.spektrum.fixed.model.HierarchyUnit

interface Rule {
    fun isRespectedBy(unit: HierarchyUnit): Boolean
}