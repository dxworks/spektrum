package me.drbaxr.spektrum.flexible.identifiers.rules.general

import me.drbaxr.spektrum.fixed.model.HierarchyUnit
import me.drbaxr.spektrum.flexible.identifiers.rules.Rule

class ContainsTestInIdentifier: Rule {
    override fun isRespectedBy(unit: HierarchyUnit): Boolean = unit.identifier.lowercase().contains("test")
}