package me.drbaxr.identifier

import me.drbaxr.model.HierarchyUnit

class SimpleTestIdentifier : TestIdentifier {
    override fun isTest(unit: HierarchyUnit): Boolean = unit.identifier.lowercase().contains("test")
}