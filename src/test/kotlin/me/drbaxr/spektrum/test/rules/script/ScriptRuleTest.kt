package me.drbaxr.spektrum.test.rules.script

import groovy.lang.GroovyShell
import me.drbaxr.spektrum.flexible.RelevantInformation
import me.drbaxr.spektrum.flexible.adapters.CSModelAdapter
import org.junit.Before
import org.junit.Test
import java.io.File

class ScriptRuleTest {

    @Before
    fun init() {
        CSModelAdapter("src/test/resources/inputs/honeydewCSModel_in.json").adapt()
    }

    @Test
    fun test() {
        val scriptPath = "rules/test.groovy"
        val functionName = "check"

        val unitIdentifier = "HoneydewCoreTest/IO/Writers/Exporters/ExportUtilsTests.cs->" +
                "HoneydewCoreTest.IO.Writers.Exporters->" +
                "ExportUtilsTests->" +
                "CsvCountPerLine_ShouldReturn0_WhenNoNumberIsDetected#"

        val value = GroovyShell()
            .parse(File(scriptPath))
            .invokeMethod(
                functionName,
                RelevantInformation.getCSImportModelInformation(unitIdentifier)
            )

        println(value)
    }
}