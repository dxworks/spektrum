package me.drbaxr.model

import me.drbaxr.exception.NonTestableUnitException

open class HierarchyUnit(
    val identifier: String,
    val children: MutableSet<HierarchyUnit>,
    val type: String,
    var isTestable: Boolean = false
) {
    private var coverage: Float = 0.0f

    fun getCoverage(): Float = coverage.takeIf { isTestable } ?: throw NonTestableUnitException(identifier)

    fun setCoverage(coverage: Float) {
        this.coverage = coverage.takeIf { isTestable } ?: throw NonTestableUnitException(identifier)
    }

    object HierarchyUnitTypes {
        const val METHOD = "METHOD"
        const val FOLDER = "FOLDER"
        const val FILE = "FILE"
        const val CLASS = "CLASS"
    }
}