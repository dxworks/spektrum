package me.drbaxr.spektrum.fixed.model

import me.drbaxr.spektrum.flexible.adapters.cs.model.external.MethodCS

class HierarchyMethod(
    identifier: String,
    val callers: MutableMap<String, Int>
) : HierarchyUnit(identifier, mutableSetOf(), GeneralHierarchyUnitTypes.METHOD) {
    companion object {
        fun methodCSFullNameToName(fullName: String): String =
            "${MethodCS.file(fullName)}$childSeparator" +
            "${MethodCS.namespace(fullName)}$childSeparator" +
            "${MethodCS.className(fullName)}$childSeparator" +
            MethodCS.method(fullName)
    }
}