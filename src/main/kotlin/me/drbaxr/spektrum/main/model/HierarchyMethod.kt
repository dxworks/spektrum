package me.drbaxr.spektrum.main.model

class HierarchyMethod(
    identifier: String,
    val callers: MutableSet<String>
) : HierarchyUnit(identifier, mutableSetOf(), GeneralHierarchyUnitTypes.METHOD)