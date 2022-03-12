package me.drbaxr.spektrum.test

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import me.drbaxr.spektrum.fixed.central.CoverageModelCalculator
import me.drbaxr.spektrum.fixed.central.MetricsExporter
import me.drbaxr.spektrum.fixed.central.UnitClassifier
import me.drbaxr.spektrum.flexible.identifiers.TestIdentifier
import me.drbaxr.spektrum.flexible.identifiers.rules.ContainsTestInIdentifier
import me.drbaxr.spektrum.fixed.model.ExportUnit
import me.drbaxr.spektrum.test.util.TestUtil.Companion.getMock
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

    @Test
    fun testOrderSimpleModel() {
        assertTrue { getComparisonWithExpectedViaFixedProgramRun("orderSimpleModel") }
    }

    private fun getComparisonWithExpectedViaFixedProgramRun(runName: String): Boolean {
        val units = getMock(FileReader("src/test/resources/inputs/${runName}_in.json"))
        val identifier = TestIdentifier(listOf(
            ContainsTestInIdentifier(),
        ))

        val split = UnitClassifier().classify(units, identifier)
        val coveredModel = CoverageModelCalculator().calculate(split.first, split.second)
        val exportedModel = MetricsExporter().getExportModel(coveredModel)

        val exportedModelType = object : TypeToken<Set<ExportUnit>>() {}.type
        val expectedModel = Gson().fromJson<Set<ExportUnit>>(
            FileReader("src/test/resources/outputs/${runName}_exp.json"),
            exportedModelType
        )

        return exportedModel == expectedModel
    }
}