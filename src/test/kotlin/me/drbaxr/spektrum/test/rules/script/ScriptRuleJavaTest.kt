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
                "bla.bla${HierarchyUnit.childSeparator}" +
                "Test1${HierarchyUnit.childSeparator}" +
                "amethod()"

        val unit = HierarchyUnit(identifier, mutableSetOf(), "METHOD")

        val rule = ScriptRuleJava("rules/java/rule.groovy")

        assertTrue { rule.isRespectedBy(unit) }
    }
}