package me.drbaxr.identifier

import me.drbaxr.model.HierarchyUnit

interface TestIdentifier {
    fun isTest(unit: HierarchyUnit): Boolean
}