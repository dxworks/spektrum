package me.drbaxr.spektrum.fixed.central

import me.drbaxr.spektrum.fixed.model.HierarchyMethod
import me.drbaxr.spektrum.flexible.identifiers.TestIdentifier
import me.drbaxr.spektrum.fixed.model.HierarchyUnit

class UnitClassifier {

    // fst - #Test test methods, snd - #Testable all units
    fun classify(
        hierarchyUnits: Set<HierarchyUnit>,
        testIdentifier: TestIdentifier
    ) {
        // TODO: cleanup
        val testMethods = mutableSetOf<HierarchyMethod>()
        val testableUnits = mutableSetOf<HierarchyUnit>()

        hierarchyUnits.forEach {
            testMethods.addAll(getTestMethods(it, testIdentifier))

            testableUnits.add(it)
        }
    }

    private fun getTestMethods(unit: HierarchyUnit, testIdentifier: TestIdentifier): Set<HierarchyMethod> {
        val testMethods = mutableSetOf<HierarchyMethod>()

        if (unit.type == HierarchyUnit.GeneralHierarchyUnitTypes.METHOD && unit is HierarchyMethod) {
            if (testIdentifier.isTest(unit)) {
                unit.isTestable = false
                testMethods.add(unit)
            }
        } else {
            unit.children.forEach {
                val test = getTestMethods(it, testIdentifier)
                testMethods.addAll(test)
            }
        }

        return testMethods
    }
}