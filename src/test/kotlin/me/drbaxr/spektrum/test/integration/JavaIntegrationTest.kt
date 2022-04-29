package me.drbaxr.spektrum.test.integration

import me.drbaxr.spektrum.fixed.central.CoverageModelCalculator
import me.drbaxr.spektrum.fixed.central.MetricsExporter
import me.drbaxr.spektrum.fixed.central.UnitClassifier
import me.drbaxr.spektrum.flexible.adapters.java.JavaModelAdapter
import me.drbaxr.spektrum.flexible.identifiers.TestIdentifier
import me.drbaxr.spektrum.flexible.identifiers.rules.java.ScriptRuleJava
import org.junit.Test

class JavaIntegrationTest {

    @Test
    fun integrationTest() {
        val adapter = JavaModelAdapter("src/test/resources/inputs/jafaxInsiderJavaModel_in.json")

        val model = adapter.adapt()
        val javaTestIdentifier = buildTestIdentifier()

        UnitClassifier().classify(model, javaTestIdentifier)
        val coveredModel = CoverageModelCalculator().calculate(model)

        val exportModel = MetricsExporter().getExportModel(coveredModel)

        TODO("assertion after rule is made")
    }

    private fun buildTestIdentifier(): TestIdentifier {
        val rule = ScriptRuleJava("rules/java/rule.groovy")

        return TestIdentifier(listOf(rule))
    }
}