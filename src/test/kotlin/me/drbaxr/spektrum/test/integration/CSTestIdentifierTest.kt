package me.drbaxr.spektrum.test.integration

import me.drbaxr.spektrum.fixed.central.CoverageModelCalculator
import me.drbaxr.spektrum.fixed.central.MetricsExporter
import me.drbaxr.spektrum.fixed.central.UnitClassifier
import me.drbaxr.spektrum.flexible.adapters.CSModelAdapter
import me.drbaxr.spektrum.flexible.identifiers.TestIdentifier
import me.drbaxr.spektrum.flexible.identifiers.rules.composite.And
import me.drbaxr.spektrum.flexible.identifiers.rules.cs.HasSomeAttribute
import me.drbaxr.spektrum.flexible.identifiers.rules.cs.HasSomeUsingStatements
import org.junit.Test

class CSTestIdentifierTest {

    @Test
    fun testCSTestIdentifier() {
        val adapter = CSModelAdapter("src/test/resources/inputs/honeydewCSModel_in.json")

        // C# part
        val model = adapter.adapt()
        val csTestIdentifier = buildTestIdentifier();

        // Fixed part
        val split = UnitClassifier().classify(model, csTestIdentifier)
        val coveredModel = CoverageModelCalculator().calculate(split.first, split.second)

        val test = MetricsExporter().getExportModel(coveredModel)
        MetricsExporter().exportAndSave(coveredModel)
    }

    private fun buildTestIdentifier(): TestIdentifier {
        val usingStatementsRule = HasSomeUsingStatements(setOf("Xunit"))
        val attributesRule = HasSomeAttribute(setOf("Xunit.FactAttribute"))
        val andRule = And(listOf(usingStatementsRule, attributesRule))

        return TestIdentifier(mutableListOf(andRule))
    }

}