package me.drbaxr

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import me.drbaxr.central.CoverageModelCalculator
import me.drbaxr.central.MetricsExporter
import me.drbaxr.central.UnitClassifier
import me.drbaxr.identifier.SimpleTestIdentifier
import me.drbaxr.mock.MockHierarchyUnit
import me.drbaxr.model.HierarchyUnit
import me.drbaxr.model.Method
import java.io.FileReader

fun main() {
    val units = getMock(FileReader("inputs/input.json"))

    val split = UnitClassifier().classify(units, SimpleTestIdentifier())
    val coveredModel = CoverageModelCalculator().calculate(split.first, split.second)
    MetricsExporter().export(coveredModel)
}

// everything under this will be removed
fun getMock(reader: FileReader): Set<HierarchyUnit> {
    val type = object : TypeToken<Set<MockHierarchyUnit>>() {}.type
    val mockSet = Gson().fromJson<Set<MockHierarchyUnit>>(
        reader,
        type
    )

    return castUnitSet(mockSet)
}

fun castUnitSet(set: Set<MockHierarchyUnit>): Set<HierarchyUnit> {
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

fun setParents(units: Set<HierarchyUnit>) {
    units.forEach { unit ->
        unit.children.forEach { it.parent = unit }
        if (unit.type != HierarchyUnit.HierarchyUnitTypes.METHOD && unit !is Method)
            setParents(unit.children)
    }
}

fun castChildren(children: Set<MockHierarchyUnit>): MutableSet<HierarchyUnit> {
    val outSet = mutableSetOf<HierarchyUnit>()

    if (children.all { it.type == HierarchyUnit.HierarchyUnitTypes.METHOD })
        children.forEach { outSet.add(Method(it.identifier, it.callers!!)) }
    else {
        children.forEach{
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