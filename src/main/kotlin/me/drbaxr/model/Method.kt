package me.drbaxr.model

class Method(
    identifier: String,
    val callers: MutableSet<Method>
) : HierarchyUnit(identifier, mutableSetOf(), HierarchyUnitTypes.METHOD)