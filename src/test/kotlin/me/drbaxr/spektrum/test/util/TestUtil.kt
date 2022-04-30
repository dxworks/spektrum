package me.drbaxr.spektrum.test.util

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import me.drbaxr.spektrum.fixed.mock.MockHierarchyUnit
import me.drbaxr.spektrum.fixed.model.HierarchyMethod
import me.drbaxr.spektrum.fixed.model.HierarchyUnit
import java.io.FileReader
import java.io.FileWriter

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

        fun writeModelToFile(model: Set<HierarchyUnit>, fileName: String) {
            val exp = model.map { convertToParsable(it) }
            val gson = GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create()
            val writer = FileWriter(fileName)
            gson.toJson(exp, writer)

            writer.flush()
            writer.close()
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

        private fun convertToParsable(unit: HierarchyUnit): MockHierarchyUnit {
            val newUnit = if (unit is HierarchyMethod) {
                MockHierarchyUnit(
                    unit.identifier,
                    null,
                    unit.type,
                    unit.isTestable,
                    unit.callers
                )
            } else {
                MockHierarchyUnit(
                    unit.identifier,
                    mutableSetOf(),
                    unit.type,
                    true,
                    null
                )
            }

            if (newUnit.children != null) {
                unit.children.forEach {
                    val newChild = convertToParsable(it)
                    newUnit.children!!.add(newChild)
                }
            }

            return newUnit
        }
    }
}