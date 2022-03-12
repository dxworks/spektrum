package me.drbaxr.spektrum.identifiers.rules

import me.drbaxr.spektrum.main.model.HierarchyUnit

class ContainsTestInIdentifier: Rule {
    override fun isRespectedBy(unit: HierarchyUnit): Boolean = unit.identifier.lowercase().contains("test")
}