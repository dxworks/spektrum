package me.drbaxr.spektrum.identifiers.rules

import me.drbaxr.spektrum.main.model.HierarchyUnit

interface Rule {
    fun isRespectedBy(unit: HierarchyUnit): Boolean
}