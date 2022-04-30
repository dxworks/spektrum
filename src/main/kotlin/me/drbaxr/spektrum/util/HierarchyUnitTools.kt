package me.drbaxr.spektrum.util

import me.drbaxr.spektrum.fixed.exception.NonTestableUnitException
import me.drbaxr.spektrum.fixed.model.ExportUnit
import me.drbaxr.spektrum.fixed.model.HierarchyUnit
import me.drbaxr.spektrum.fixed.model.HierarchyMethod

class HierarchyUnitTools {

    companion object {
        fun getTypeUnits(model: Set<HierarchyUnit>, type: String): Set<HierarchyUnit> {
            val unitsSet = mutableSetOf<HierarchyUnit>()

            model.forEach { unit ->
                if (unit.type == type)
                    unitsSet.add(unit)

                unitsSet.addAll(getTypeUnits(unit.children, type))
            }

            return unitsSet
        }

        fun mapToExport(model: Set<HierarchyUnit>): Set<ExportUnit> {
            val exportSet = mutableSetOf<ExportUnit>()

            model.forEach { unit ->
                val exUnit: ExportUnit

                val coverage = try {
                    unit.getCoverage()
                } catch (e: NonTestableUnitException) {
                    -1.0f
                }

                if (unit.type == HierarchyUnit.GeneralHierarchyUnitTypes.METHOD && unit is HierarchyMethod) {
                    val testAmount = 1f.takeIf { !unit.isTestable } ?: 0f

                    exUnit = ExportUnit(unit.identifier, unit.type, coverage, testAmount, null)
                } else {
                    exUnit = ExportUnit(unit.identifier, unit.type, coverage, getHierarchyUnitTestAmount(unit), mutableSetOf())
                    exUnit.children?.addAll(mapToExport(unit.children))
                }

                exportSet.add(exUnit)
            }

            return exportSet
        }

        private fun getHierarchyUnitTestAmount(unit: HierarchyUnit): Float {
            if (unit is HierarchyMethod)
                return 1f.takeIf { !unit.isTestable } ?: 0f

            val childrenTestAmount = unit.children.map { getHierarchyUnitTestAmount(it) }

            return childrenTestAmount.average().toFloat().takeIf { !it.isNaN() } ?: 0f
        }
    }

}