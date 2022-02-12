package me.drbaxr.spektrum.util

import me.drbaxr.spektrum.main.model.ExportUnit
import me.drbaxr.spektrum.main.model.HierarchyUnit
import me.drbaxr.spektrum.main.model.Method

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

                if (unit.type == HierarchyUnit.HierarchyUnitTypes.METHOD && unit is Method) {
                    exUnit = ExportUnit(unit.identifier, unit.type, unit.getCoverage(), null)
                } else {
                    exUnit = ExportUnit(unit.identifier, unit.type, unit.getCoverage(), mutableSetOf())
                    exUnit.children?.addAll(mapToExport(unit.children))
                }

                exportSet.add(exUnit)
            }

            return exportSet
        }
    }

}