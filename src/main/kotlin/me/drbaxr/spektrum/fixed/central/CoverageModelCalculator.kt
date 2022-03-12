package me.drbaxr.spektrum.fixed.central

import me.drbaxr.spektrum.fixed.model.HierarchyUnit
import me.drbaxr.spektrum.fixed.model.HierarchyMethod
import me.drbaxr.spektrum.util.HierarchyUnitTools

class CoverageModelCalculator {

    fun calculate(test: Set<HierarchyUnit>, testable: Set<HierarchyUnit>): Set<HierarchyUnit> {
        val testMethods =
            HierarchyUnitTools.getTypeUnits(test, HierarchyUnit.GeneralHierarchyUnitTypes.METHOD).map { it as HierarchyMethod }.toSet()
        val testableMethods =
            HierarchyUnitTools.getTypeUnits(testable, HierarchyUnit.GeneralHierarchyUnitTypes.METHOD).map { it as HierarchyMethod }.toSet()

        setCoverage(testable, testMethods)
        aggregateCoverage(testableMethods)

        return testable
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

    private fun setCoverage(testable: Set<HierarchyUnit>, testMethods: Set<HierarchyMethod>) {
        testable.forEach {
            if (it.type == HierarchyUnit.GeneralHierarchyUnitTypes.METHOD && it is HierarchyMethod) {
                it.setCoverage(getMethodCoverage(it, testMethods))
            } else {
                setCoverage(it.children, testMethods)
            }
        }
    }

    private fun getMethodCoverage(method: HierarchyMethod, testMethods: Set<HierarchyMethod>): Float {
        val testedMethods = method.callers.filter { caller -> testMethods.any { caller.key == it.identifier } }
        val minOrder = testedMethods.values.minOrNull() ?: return 0f

        return 1f / minOrder
    }
}