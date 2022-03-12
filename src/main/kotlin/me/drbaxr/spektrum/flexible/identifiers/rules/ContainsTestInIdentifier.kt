package me.drbaxr.spektrum.flexible.identifiers.rules

import me.drbaxr.spektrum.fixed.model.HierarchyUnit

class ContainsTestInIdentifier: Rule {
    override fun isRespectedBy(unit: HierarchyUnit): Boolean = unit.identifier.lowercase().contains("test")
}