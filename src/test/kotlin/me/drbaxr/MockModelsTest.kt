package me.drbaxr

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import me.drbaxr.central.CoverageModelCalculator
import me.drbaxr.central.MetricsExporter
import me.drbaxr.central.UnitClassifier
import me.drbaxr.identifier.SimpleTestIdentifier
import me.drbaxr.mock.MockHierarchyUnit
import me.drbaxr.model.ExportUnit
import me.drbaxr.model.HierarchyUnit
import me.drbaxr.model.Method
import org.junit.Test
import java.io.FileReader
import kotlin.test.assertTrue

class MockModelsTest {

    @Test
    fun testSimpleModel() {
        assertTrue { getComparisonWithExpectedViaFixedProgramRun("simpleModel") }
    }

    @Test
    fun testSimpleModelCS() {
        assertTrue { getComparisonWithExpectedViaFixedProgramRun("simpleModelCS") }
    }

    private fun getComparisonWithExpectedViaFixedProgramRun(runName: String): Boolean {
        val units = getMock(FileReader("src/test/resources/inputs/${runName}_in.json"))

        val split = UnitClassifier().classify(units, SimpleTestIdentifier())
        val coveredModel = CoverageModelCalculator().calculate(split.first, split.second)
        val exportedModel = MetricsExporter().getExportModel(coveredModel)

        val exportedModelType = object : TypeToken<Set<ExportUnit>>() {}.type
        val expectedModel = Gson().fromJson<Set<ExportUnit>>(
            FileReader("src/test/resources/outputs/${runName}_exp.json"),
            exportedModelType
        )

        return exportedModel == expectedModel
    }

    private fun getMock(reader: FileReader): Set<HierarchyUnit> {
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
            if (unit.type != HierarchyUnit.HierarchyUnitTypes.METHOD && unit !is Method)
                setParents(unit.children)
        }
    }

    private fun castChildren(children: Set<MockHierarchyUnit>): MutableSet<HierarchyUnit> {
        val outSet = mutableSetOf<HierarchyUnit>()

        if (children.all { it.type == HierarchyUnit.HierarchyUnitTypes.METHOD })
            children.forEach { outSet.add(Method(it.identifier, it.callers!!)) }
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