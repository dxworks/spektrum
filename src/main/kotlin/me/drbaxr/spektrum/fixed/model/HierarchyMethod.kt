package me.drbaxr.spektrum.fixed.model

import me.drbaxr.spektrum.flexible.adapters.model.external.Method

class HierarchyMethod(
    identifier: String,
    val callers: MutableMap<String, Int>
) : HierarchyUnit(identifier, mutableSetOf(), GeneralHierarchyUnitTypes.METHOD) {
    companion object {
        fun methodFullNameToName(fullName: String): String =
            "${Method.file(fullName)}$childSeparator" +
            "${Method.namespace(fullName)}$childSeparator" +
            "${Method.namespace(fullName)}.${Method.className(fullName)}$childSeparator" +
            Method.method(fullName)
    }
}