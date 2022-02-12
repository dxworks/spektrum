package me.drbaxr.spektrum.main.identifier

import me.drbaxr.spektrum.main.model.HierarchyUnit

interface TestIdentifier {
    fun isTest(unit: HierarchyUnit): Boolean
}