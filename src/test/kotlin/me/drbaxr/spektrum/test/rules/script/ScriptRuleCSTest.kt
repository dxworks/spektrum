package me.drbaxr.spektrum.test.rules.script

import me.drbaxr.spektrum.fixed.model.HierarchyUnit
import me.drbaxr.spektrum.flexible.adapters.CSModelAdapter
import me.drbaxr.spektrum.flexible.identifiers.rules.cs.ScriptRuleCS
import org.junit.Before
import org.junit.Test

class ScriptRuleCSTest {

    @Before
    fun init() {
        CSModelAdapter("src/test/resources/inputs/honeydewCSModel_in.json").adapt()
    }

    @Test
    fun test() {
        val unitIdentifier = "HoneydewCoreTest/IO/Writers/Exporters/ExportUtilsTests.cs->" +
                "HoneydewCoreTest.IO.Writers.Exporters->" +
                "ExportUtilsTests->" +
                "CsvCountPerLine_ShouldReturn0_WhenNoNumberIsDetected#"

        val unit = HierarchyUnit(
            unitIdentifier,
            mutableSetOf(),
            "METHOD"
        )

        val rule = ScriptRuleCS("rules/test.groovy")

        println(rule.isRespectedBy(unit))
    }
}