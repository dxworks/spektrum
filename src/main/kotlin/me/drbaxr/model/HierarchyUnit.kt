package me.drbaxr.model

import me.drbaxr.exception.NonTestableUnitException

open class HierarchyUnit(
    val identifier: String,
    val children: MutableSet<HierarchyUnit>,
    val type: String,
    var isTestable: Boolean = true, // shouldn't be changed outside of unit classifier
    var parent: HierarchyUnit? = null // shouldn't be changed outside of model adapter
) {
    private var coverage: Float = 0.0f

    object HierarchyUnitTypes {
        const val FOLDER = "FOLDER"
        const val FILE = "FILE"
        const val CLASS = "CLASS"
        const val METHOD = "METHOD"
    }

    fun getCoverage(): Float = coverage.takeIf { isTestable } ?: throw NonTestableUnitException(identifier)

    // shouldn't be called outside of coverage model calculator
    fun setCoverage(coverage: Float) {
        this.coverage = coverage.takeIf { isTestable } ?: throw NonTestableUnitException(identifier)
    }

    override fun toString(): String {
        return "[$type] $identifier"
    }

    fun toPrettyString(): String {
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
        "[${hierarchyUnit.type}] ${"[TEST]".takeIf { !isTestable } ?: "[${hierarchyUnit.getCoverage()}]"} ${hierarchyUnit.identifier}"
}