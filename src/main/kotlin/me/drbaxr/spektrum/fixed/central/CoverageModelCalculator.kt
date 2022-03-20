package me.drbaxr.spektrum.fixed.central

import me.drbaxr.spektrum.fixed.exception.NonTestableUnitException
import me.drbaxr.spektrum.fixed.model.HierarchyUnit
import me.drbaxr.spektrum.fixed.model.HierarchyMethod
import me.drbaxr.spektrum.util.HierarchyUnitTools

class CoverageModelCalculator {

    fun calculate(units: Set<HierarchyUnit>): Set<HierarchyUnit> {
        val modelMethods = HierarchyUnitTools.getTypeUnits(units, HierarchyUnit.GeneralHierarchyUnitTypes.METHOD)
            .map { it as HierarchyMethod }

        val testableMethods = modelMethods
            .filter { it.isTestable }
            .toSet()
        val testMethods = modelMethods
            .filter { !it.isTestable }
            .toSet()

        setCoverage(testMethods, testableMethods)
        aggregateCoverage(testableMethods)

        return units
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
                p.children.map {
                    try {
                        it.getCoverage()
                    } catch (e: NonTestableUnitException) {
                        0f
                    }
                }.average().toFloat()
            )
        }

        if (parents.size > 0)
            aggregateCoverage(parents)
    }

    private fun setCoverage(test: Set<HierarchyMethod>, testable: Set<HierarchyMethod>) {
        testable.forEach {
            it.setCoverage(getMethodCoverage(it, test))
        }
    }

    private fun getMethodCoverage(method: HierarchyMethod, testMethods: Set<HierarchyMethod>): Float {
        val testedMethods = method.callers.filter { caller -> testMethods.any { caller.key == it.identifier } }
        val minOrder = testedMethods.values.minOrNull() ?: return 0f

        return 1f / minOrder
    }
}