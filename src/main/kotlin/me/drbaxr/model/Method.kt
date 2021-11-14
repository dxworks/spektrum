package me.drbaxr.model

class Method(
    identifier: String,
    val callers: MutableSet<String>
) : HierarchyUnit(identifier, mutableSetOf(), HierarchyUnitTypes.METHOD) {
    override fun toString(): String {
        return "Method(identifier=$identifier callers=$callers)"
    }
}