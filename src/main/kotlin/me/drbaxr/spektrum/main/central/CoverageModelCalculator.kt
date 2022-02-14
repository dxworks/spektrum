package me.drbaxr.spektrum.main.central

import me.drbaxr.spektrum.main.model.HierarchyUnit
import me.drbaxr.spektrum.main.model.HierarchyMethod
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
                if (isTested(it, testMethods)) {
                    it.setCoverage(1.0f)
                }
            } else {
                setCoverage(it.children, testMethods)
            }
        }
    }

    private fun isTested(method: HierarchyMethod, testMethods: Set<HierarchyMethod>): Boolean {
        return method.callers.any { caller -> testMethods.any { caller.key == it.identifier } }
    }

}