package me.drbaxr.spektrum.flexible.identifiers.rules.java

import groovy.lang.GroovyShell
import groovy.lang.Script
import me.drbaxr.spektrum.fixed.model.HierarchyUnit
import me.drbaxr.spektrum.flexible.RelevantInformation
import me.drbaxr.spektrum.flexible.identifiers.rules.Rule
import java.io.File
import java.lang.Exception

class ScriptRuleJava(private val scriptPath: String) : Rule {
    private val script: Script = GroovyShell().parse(File(scriptPath))

    override fun isRespectedBy(unit: HierarchyUnit): Boolean {
        val ruleValue = script.invokeMethod(
            Rule.scriptRuleEntryMethodName,
            RelevantInformation.getJavaUnitInformation(unit.identifier)
        )

        if (ruleValue is Boolean) {
            return ruleValue
        } else {
            throw Exception("Script $scriptPath's '${Rule.scriptRuleEntryMethodName}' method does not return a boolean")
        }
    }
}