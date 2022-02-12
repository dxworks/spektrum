package me.drbaxr.spektrum.identifiers

import me.drbaxr.spektrum.main.model.HierarchyUnit

interface TestIdentifier {
    fun isTest(unit: HierarchyUnit): Boolean
}