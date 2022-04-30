package me.drbaxr.spektrum.test.integration

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import me.drbaxr.spektrum.fixed.central.CoverageModelCalculator
import me.drbaxr.spektrum.fixed.central.MetricsExporter
import me.drbaxr.spektrum.fixed.central.UnitClassifier
import me.drbaxr.spektrum.fixed.model.ExportUnit
import me.drbaxr.spektrum.flexible.adapters.java.JavaModelAdapter
import me.drbaxr.spektrum.flexible.identifiers.TestIdentifier
import me.drbaxr.spektrum.flexible.identifiers.rules.java.ScriptRuleJava
import org.junit.Test
import java.io.FileReader
import kotlin.test.assertTrue

class JavaIntegrationTest {

    @Test
    fun integrationTest() {
        val adapter = JavaModelAdapter("src/test/resources/inputs/jafaxInsiderJavaModel_in.json")

        val model = adapter.adapt()
        val javaTestIdentifier = buildTestIdentifier()

        UnitClassifier().classify(model, javaTestIdentifier)
        val coveredModel = CoverageModelCalculator().calculate(model)

        val exportModel = MetricsExporter().getExportModel(coveredModel)

        val exportedModelType = object : TypeToken<Set<ExportUnit>>() {}.type
        val expectedExportModel = Gson().fromJson<Set<ExportUnit>>(
            FileReader("src/test/resources/outputs/jafaxInsiderJavaIntegration_exp.json"),
            exportedModelType
        )

        assertTrue { expectedExportModel == exportModel }
    }

    private fun buildTestIdentifier(): TestIdentifier {
        val rule = ScriptRuleJava("rules/java/rule.groovy")

        return TestIdentifier(listOf(rule))
    }
}