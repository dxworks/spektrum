package me.drbaxr.spektrum.main.model

class HierarchyMethod(
    identifier: String,
    val callers: MutableMap<String, Int>
) : HierarchyUnit(identifier, mutableSetOf(), GeneralHierarchyUnitTypes.METHOD)