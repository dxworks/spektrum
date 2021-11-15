package me.drbaxr.central

import me.drbaxr.identifier.TestIdentifier
import me.drbaxr.model.HierarchyUnit

class UnitClassifier {

    // fst - #Test, snd - #Testable
    fun classify(
        hierarchyUnits: Set<HierarchyUnit>,
        testIdentifier: TestIdentifier
    ): Pair<Set<HierarchyUnit>, Set<HierarchyUnit>> {
        val testUnits = mutableSetOf<HierarchyUnit>()
        val testableUnits = mutableSetOf<HierarchyUnit>()

        hierarchyUnits.forEach {
            if (testIdentifier.isTest(it)) {
                testUnits.add(it)
                setUnitAsTest(it)
            } else {
                testableUnits.add(it)
            }
        }

        return Pair(testUnits, testableUnits)
    }

    private fun setUnitAsTest(unit: HierarchyUnit) {
        if (unit.type != HierarchyUnit.HierarchyUnitTypes.METHOD) {
            unit.isTestable = false
            unit.children.forEach { setUnitAsTest(it) }
        }
    }

}