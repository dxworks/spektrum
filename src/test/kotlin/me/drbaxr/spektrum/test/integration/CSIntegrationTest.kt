package me.drbaxr.spektrum.test.integration

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import me.drbaxr.spektrum.fixed.central.CoverageModelCalculator
import me.drbaxr.spektrum.fixed.central.MetricsExporter
import me.drbaxr.spektrum.fixed.central.UnitClassifier
import me.drbaxr.spektrum.fixed.model.ExportUnit
import me.drbaxr.spektrum.flexible.adapters.cs.CSModelAdapter
import me.drbaxr.spektrum.flexible.identifiers.TestIdentifier
import me.drbaxr.spektrum.flexible.identifiers.rules.composite.And
import me.drbaxr.spektrum.flexible.identifiers.rules.cs.HasSomeAttribute
import me.drbaxr.spektrum.flexible.identifiers.rules.cs.HasSomeUsingStatements
import me.drbaxr.spektrum.flexible.identifiers.rules.cs.ScriptRuleCS
import org.junit.Test
import java.io.FileReader
import kotlin.test.assertTrue

class CSIntegrationTest {

    @Test
    fun testCSTestIdentifier() {
        val adapter = CSModelAdapter("src/test/resources/inputs/honeydewCSModel_in.json")

        // C# part
        val model = adapter.adapt()
        val csTestIdentifier = buildTestIdentifier()

        // Fixed part
        UnitClassifier().classify(model, csTestIdentifier)
        val coveredModel = CoverageModelCalculator().calculate(model)

        val exportModel = MetricsExporter().getExportModel(coveredModel)

        val exportedModelType = object : TypeToken<Set<ExportUnit>>() {}.type
        val expectedExportModel = Gson().fromJson<Set<ExportUnit>>(
            FileReader("src/test/resources/outputs/honeydewCSModelIntegration_exp.json"),
            exportedModelType
        )

        MetricsExporter().exportAndSave(coveredModel)

        assertTrue { expectedExportModel == exportModel }
    }

    private fun buildTestIdentifier(): TestIdentifier {
//         faster
//        val usingStatementsRule = HasSomeUsingStatements(setOf("Xunit"))
//        val attributesRule = HasSomeAttribute(setOf("Xunit.FactAttribute", "Xunit.TheoryAttribute"))
//        val rule = And(listOf(usingStatementsRule, attributesRule))
        // more customizable
        val rule = ScriptRuleCS("src/test/resources/rules/cs/rule.groovy")

        return TestIdentifier(mutableListOf(rule))
    }

}