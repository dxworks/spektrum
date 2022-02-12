package me.drbaxr.spektrum.identifiers

import me.drbaxr.spektrum.main.model.HierarchyUnit

class SimpleTestIdentifier : TestIdentifier {
    override fun isTest(unit: HierarchyUnit): Boolean = unit.identifier.lowercase().contains("test")
}