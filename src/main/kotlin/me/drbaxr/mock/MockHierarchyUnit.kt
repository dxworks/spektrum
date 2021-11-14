package me.drbaxr.mock

data class MockHierarchyUnit(
    var identifier: String,
    var children: MutableSet<MockHierarchyUnit>,
    var type: String,
    var isTestable: Boolean = false,
    var callers: MutableSet<String>
)