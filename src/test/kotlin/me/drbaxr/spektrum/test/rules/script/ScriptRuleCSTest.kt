package me.drbaxr.spektrum.test.rules.script

import me.drbaxr.spektrum.fixed.model.HierarchyUnit
import me.drbaxr.spektrum.flexible.adapters.cs.CSModelAdapter
import me.drbaxr.spektrum.flexible.identifiers.rules.cs.ScriptRuleCS
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ScriptRuleCSTest {

    @Before
    fun init() {
        CSModelAdapter("src/test/resources/inputs/honeydewCSModel_in.json").adapt()
    }

    @Test
    fun testScriptRuleCSTrue() {
        val unitIdentifier = "HoneydewCoreTest/IO/Writers/Exporters/ExportUtilsTests.cs->" +
                "HoneydewCoreTest.IO.Writers.Exporters->" +
                "ExportUtilsTests->" +
                "CsvCountPerLine_ShouldReturn0_WhenNoNumberIsDetected#"

        val unit = HierarchyUnit(
            unitIdentifier,
            mutableSetOf(),
            "METHOD"
        )

        val rule = ScriptRuleCS("rules/cs/rule.groovy")

        assertTrue { rule.isRespectedBy(unit) }
    }

    @Test
    fun testScriptRuleCSFalse() {
        val unitIdentifier = "HoneydewModels/CSharp/ClassModel.cs->" +
                "HoneydewModels.CSharp->" +
                "ClassModel->" +
                "get#"

        val unit = HierarchyUnit(
            unitIdentifier,
            mutableSetOf(),
            "METHOD"
        )

        val rule = ScriptRuleCS("rules/cs/rule.groovy")

        assertFalse { rule.isRespectedBy(unit) }
    }
}