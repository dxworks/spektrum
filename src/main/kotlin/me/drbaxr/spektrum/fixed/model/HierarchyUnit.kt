package me.drbaxr.spektrum.fixed.model

import me.drbaxr.spektrum.fixed.exception.NonTestableUnitException

open class HierarchyUnit(
    val identifier: String, // identifier MUST be the result that has a mapping oh 1-to-1 and is reversible (to be able to easily obtain parent name and local name of unit)
    val children: MutableSet<HierarchyUnit>,
    val type: String,
    var parent: HierarchyUnit? = null // shouldn't be changed outside of model adapter
) {
    companion object {
        const val childSeparator = "->"
    }

    private var coverage: Float = 0.0f
    private var testAmount: Float = 0.0f

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

    object JavaHierarchyUnitTypes {
        const val PROJECT = "PROJECT"
        const val PACKAGE = "PACKAGE"
        const val CLASS = "CLASS"
        const val METHOD = "METHOD"
    }

    fun getCoverage(): Float = coverage

    // shouldn't be called outside of coverage model calculator
    fun setCoverage(coverage: Float) {
        this.coverage = coverage
    }

    fun getTestAmount(): Float = testAmount

    fun setTestAmount(testAmount: Float) {
        this.testAmount = testAmount
    }

    override fun toString(): String {
        return "[$type] $identifier"
    }

    fun toPrettyString(short: Boolean = false): String {
        var finalString = printStyle(this, short)
        children.forEach { finalString += "\n${recursivePrint(it, 1, short)}" }

        return finalString
    }

    // TODO: test this more
    fun isEqual(unit: HierarchyUnit): Boolean {
        if (unit is HierarchyMethod) {
            if (this !is HierarchyMethod)
                return false

            if (identifier != unit.identifier)
                return false

            var eq = true
            callers.forEach { (method, order) ->
                if (unit.callers[method] != order)
                    eq = false
            }

            return eq
        } else {
            if (identifier != unit.identifier)
                return false

            var eq = true
            children.forEach { otherChild ->
                val child = children.find { it.identifier == otherChild.identifier }
                if (child?.isEqual(otherChild) != true)
                    eq = false
            }

            return eq
        }
    }

    private fun recursivePrint(hierarchyUnit: HierarchyUnit, indentCount: Int, short: Boolean): String {
        val indentation = "\t".repeat(indentCount)
        var finalString = "$indentation${printStyle(hierarchyUnit, short)}"

        if (hierarchyUnit.type != GeneralHierarchyUnitTypes.METHOD) {
            hierarchyUnit.children.forEach { finalString += "\n${recursivePrint(it, indentCount + 1, short)}" }
        } else {
            val hierarchyMethod = hierarchyUnit as HierarchyMethod
            hierarchyMethod.callers.forEach { finalString += "\n$indentation\t[CALL] [${it.value}] ${it.key}" }
        }

        return finalString
    }

    private fun printStyle(hierarchyUnit: HierarchyUnit, short: Boolean): String {
        val identifier = if (short) {
            hierarchyUnit.identifier.split(childSeparator).last()
        } else {
            hierarchyUnit.identifier
        }
        
        return "[${hierarchyUnit.type}] [${hierarchyUnit.getCoverage()}] $identifier"
    }
}