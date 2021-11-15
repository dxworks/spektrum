package me.drbaxr.central

import me.drbaxr.model.HierarchyUnit
import me.drbaxr.model.Method

class CoverageModelCalculator {

    fun calculate(test: Set<HierarchyUnit>, testable: Set<HierarchyUnit>) {
        val testMethods = mutableSetOf<Method>()
        val testableMethods = mutableSetOf<Method>()

        test.forEach { testMethods.addAll(extractMethods(it)) }
        testable.forEach { testableMethods.addAll(extractMethods(it)) }

        setCoverage(testable, testMethods)
        aggregateCoverage(testableMethods)
    }

    private fun aggregateCoverage(units: Set<HierarchyUnit>) {
        val parents = mutableSetOf<HierarchyUnit>()

        units.forEach { unit ->
            val parent = unit.parent
            if (parent != null && parents.none { it.identifier == parent.identifier }) {
                parents.add(parent)
            }
        }

        parents.forEach { p ->
            p.setCoverage(
                p.children.map { it.getCoverage() }.average().toFloat()
            )
        }

        if (parents.size > 0)
            aggregateCoverage(parents)
    }

    private fun setCoverage(testable: Set<HierarchyUnit>, testMethods: Set<Method>) {
        testable.forEach {
            if (it.type == HierarchyUnit.HierarchyUnitTypes.METHOD && it is Method) {
                if (isTested(it, testMethods)) {
                    it.setCoverage(1.0f)
                }
            } else {
                setCoverage(it.children, testMethods)
            }
        }
    }

    private fun extractMethods(unit: HierarchyUnit): Set<Method> {
        return if (unit.type == HierarchyUnit.HierarchyUnitTypes.METHOD && unit is Method) {
            setOf(unit)
        } else {
            val childrenMethods = mutableSetOf<Method>()
            unit.children.forEach { childrenMethods.addAll(extractMethods(it)) }
            childrenMethods
        }
    }

    private fun isTested(method: Method, testMethods: Set<Method>): Boolean {
        return method.callers.any { callerId -> testMethods.any { callerId == it.identifier } }
    }

}