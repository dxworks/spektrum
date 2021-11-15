package me.drbaxr.model

import me.drbaxr.exception.NonTestableUnitException

open class HierarchyUnit(
    val identifier: String,
    val children: MutableSet<HierarchyUnit>,
    val type: String,
    var isTestable: Boolean = false
) {
    private var coverage: Float = 0.0f

    object HierarchyUnitTypes {
        const val FOLDER = "FOLDER"
        const val FILE = "FILE"
        const val CLASS = "CLASS"
        const val METHOD = "METHOD"
    }

    fun getCoverage(): Float = coverage.takeIf { isTestable } ?: throw NonTestableUnitException(identifier)

    fun setCoverage(coverage: Float) {
        this.coverage = coverage.takeIf { isTestable } ?: throw NonTestableUnitException(identifier)
    }

    override fun toString(): String {
        var finalString = printStyle(this)
        children.forEach { finalString += "\n${recursivePrint(it, 1)}" }

        return finalString
    }

    private fun recursivePrint(hierarchyUnit: HierarchyUnit, indentCount: Int): String {
        val indentation = "\t".repeat(indentCount)
        var finalString = "$indentation${printStyle(hierarchyUnit)}"

        if (hierarchyUnit.type != HierarchyUnitTypes.METHOD) {
            hierarchyUnit.children.forEach { finalString += "\n${recursivePrint(it, indentCount + 1)}" }
        } else if (hierarchyUnit is Method) {
            hierarchyUnit.callers.forEach { finalString += "\n$indentation\t$it" }
        }

        return finalString
    }

    private fun printStyle(hierarchyUnit: HierarchyUnit): String =
        "[${hierarchyUnit.type}] ${hierarchyUnit.identifier}"
}