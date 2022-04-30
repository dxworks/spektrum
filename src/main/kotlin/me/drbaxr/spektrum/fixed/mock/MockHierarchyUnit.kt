package me.drbaxr.spektrum.fixed.mock

@Deprecated("Will be removed because I'm pretty sure it's no longer used")
data class MockHierarchyUnit(
    var identifier: String,
    var children: MutableSet<MockHierarchyUnit>?,
    var type: String,
    var isTestable: Boolean? = false,
    var callers: MutableMap<String, Int>?
)