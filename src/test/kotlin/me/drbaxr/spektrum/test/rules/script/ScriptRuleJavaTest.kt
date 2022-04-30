package me.drbaxr.spektrum.test.rules.script

import me.drbaxr.spektrum.fixed.model.HierarchyUnit
import me.drbaxr.spektrum.flexible.adapters.java.JavaModelAdapter
import me.drbaxr.spektrum.flexible.identifiers.rules.java.ScriptRuleJava
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue

class ScriptRuleJavaTest {
    @Before
    fun init() {
        JavaModelAdapter("src/test/resources/inputs/jafaxInsiderJavaModel_in.json").adapt()
    }

    @Test
    fun testScriptRuleJavaTrue() {
        val identifier = "D:\\PersonalProjects\\jafax\\src\\test\\resources\\insider${HierarchyUnit.childSeparator}" +
                "org.dxworks.dxplatform.plugins.insider.commands${HierarchyUnit.childSeparator}" +
                "DiagnoseCommand${HierarchyUnit.childSeparator}" +
                "parse(java.lang.String[])"

        val unit = HierarchyUnit(identifier, mutableSetOf(), "METHOD")

        val rule = ScriptRuleJava("src/test/resources/rules/java/rule.groovy")

        assertTrue { rule.isRespectedBy(unit) }
    }
}