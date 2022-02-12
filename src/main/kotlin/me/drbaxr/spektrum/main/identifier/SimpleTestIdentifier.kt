package me.drbaxr.spektrum.main.identifier

import me.drbaxr.spektrum.main.model.HierarchyUnit

class SimpleTestIdentifier : TestIdentifier {
    override fun isTest(unit: HierarchyUnit): Boolean = unit.identifier.lowercase().contains("test")
}