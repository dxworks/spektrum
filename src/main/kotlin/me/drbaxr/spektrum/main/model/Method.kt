package me.drbaxr.spektrum.main.model

class Method(
    identifier: String,
    val callers: MutableSet<String>
) : HierarchyUnit(identifier, mutableSetOf(), HierarchyUnitTypes.METHOD)