package me.drbaxr.spektrum.test.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import me.drbaxr.spektrum.main.mock.MockHierarchyUnit
import me.drbaxr.spektrum.main.model.HierarchyMethod
import me.drbaxr.spektrum.main.model.HierarchyUnit
import java.io.FileReader

class TestUtil {
    companion object {
        fun getMock(reader: FileReader): Set<HierarchyUnit> {
            val type = object : TypeToken<Set<MockHierarchyUnit>>() {}.type
            val mockSet = Gson().fromJson<Set<MockHierarchyUnit>>(
                reader,
                type
            )

            return castUnitSet(mockSet)
        }

        private fun castUnitSet(set: Set<MockHierarchyUnit>): Set<HierarchyUnit> {
            val outSet = mutableSetOf<HierarchyUnit>()

            set.forEach {
                outSet.add(
                    HierarchyUnit(
                        it.identifier,
                        castChildren(it.children!!),
                        it.type
                    )
                )
            }

            setParents(outSet)

            return outSet
        }

        private fun setParents(units: Set<HierarchyUnit>) {
            units.forEach { unit ->
                unit.children.forEach { it.parent = unit }
                if (unit.type != HierarchyUnit.GeneralHierarchyUnitTypes.METHOD && unit !is HierarchyMethod)
                    setParents(unit.children)
            }
        }

        private fun castChildren(children: Set<MockHierarchyUnit>): MutableSet<HierarchyUnit> {
            val outSet = mutableSetOf<HierarchyUnit>()


            if (children.all { it.type == HierarchyUnit.GeneralHierarchyUnitTypes.METHOD })
                children.forEach { outSet.add(HierarchyMethod(it.identifier, it.callers!!)) }
            else {
                children.forEach {
                    outSet.add(
                        HierarchyUnit(
                            it.identifier,
                            castChildren(it.children!!),
                            it.type
                        )
                    )
                }
            }

            return outSet
        }
    }
}