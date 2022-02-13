package me.drbaxr.spektrum.main.model

import me.drbaxr.spektrum.main.exception.NonTestableUnitException

open class HierarchyUnit(
    val identifier: String, // identifier MUST be the result that has a mapping oh 1-to-1 and is reversible (to be able to easily obtain parent name and local name of unit)
    val children: MutableSet<HierarchyUnit>,
    val type: String,
    var isTestable: Boolean = true, // shouldn't be changed outside of unit classifier
    var parent: HierarchyUnit? = null // shouldn't be changed outside of model adapter
) {
    companion object {
        const val childSeparator = "->"
    }

    private var coverage: Float = 0.0f

    object GeneralHierarchyUnitTypes {
        const val FOLDER = "FOLDER"
        const val FILE = "FILE"
        const val CLASS = "CLASS"
        const val METHOD = "METHOD"
    }

    object CSHierarchyUnitTypes {
        const val PROJECT = "PROJECT"
        const val FILE = "FILE"
        const val NAMESPACE = "NAMESPACE"
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

        if (hierarchyUnit.type != GeneralHierarchyUnitTypes.METHOD) {
            hierarchyUnit.children.forEach { finalString += "\n${recursivePrint(it, indentCount + 1)}" }
        } else if (hierarchyUnit is HierarchyMethod) {
            hierarchyUnit.callers.forEach { finalString += "\n$indentation\t$it" }
        }

        return finalString
    }

    private fun printStyle(hierarchyUnit: HierarchyUnit): String =
        "[${hierarchyUnit.type}] ${"[TEST]".takeIf { !isTestable } ?: "[${hierarchyUnit.getCoverage()}]"} ${hierarchyUnit.identifier}"
}