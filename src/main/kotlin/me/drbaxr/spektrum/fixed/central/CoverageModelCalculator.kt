package me.drbaxr.spektrum.fixed.central

import me.drbaxr.spektrum.fixed.exception.NonTestableUnitException
import me.drbaxr.spektrum.fixed.model.HierarchyUnit
import me.drbaxr.spektrum.fixed.model.HierarchyMethod
import me.drbaxr.spektrum.util.HierarchyUnitTools

class CoverageModelCalculator {
    enum class AggregationType {
        COVERAGE,
        TEST_AMOUNT
    }

    fun calculate(units: Set<HierarchyUnit>): Set<HierarchyUnit> {
        val modelMethods = HierarchyUnitTools.getTypeUnits(units, HierarchyUnit.GeneralHierarchyUnitTypes.METHOD)
            .map { it as HierarchyMethod }

        val testableMethods = modelMethods
            .filter { it.isTestable }
            .toSet()
        val testMethods = modelMethods
            .filter { !it.isTestable }
            .toSet()

        testMethods.forEach { it.setTestAmount(getHierarchyUnitTestAmount(it)) }

        setCoverage(testMethods, testableMethods)
        aggregate(testMethods, AggregationType.TEST_AMOUNT)
        aggregate(testableMethods, AggregationType.COVERAGE) // maybe split into aggregate coverage AND aggregate test amount and use them to their relevant sets

        return units
    }

    private fun aggregate(units: Set<HierarchyUnit>, type: AggregationType) {
        val parents = mutableSetOf<HierarchyUnit>()

        units.forEach { unit ->
            val parent = unit.parent
            if (parent != null && parents.none { it.identifier == parent.identifier }) {
                parents.add(parent)
            }
        }

        parents.forEach { p ->
            when (type) {
                AggregationType.COVERAGE -> p.setCoverage(getHierarchyUnitCoverage(p))
                AggregationType.TEST_AMOUNT -> p.setTestAmount(getHierarchyUnitTestAmount(p))
            }
        }

        if (parents.size > 0)
            aggregate(parents, type)
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

    private fun getHierarchyUnitCoverage(unit: HierarchyUnit): Float {
        return unit.children.map {
            try {
                it.getCoverage()
            } catch (e: NonTestableUnitException) {
                0f
            }
        }.average().toFloat()
    }

    private fun getHierarchyUnitTestAmount(unit: HierarchyUnit): Float {
        if (unit is HierarchyMethod)
            return 1f.takeIf { !unit.isTestable } ?: 0f

        val childrenTestAmount = unit.children.map { getHierarchyUnitTestAmount(it) }

        return childrenTestAmount.average().toFloat().takeIf { !it.isNaN() } ?: 0f
    }

}