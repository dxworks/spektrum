package me.drbaxr.spektrum.test.rules.composite

import me.drbaxr.spektrum.fixed.model.HierarchyMethod
import me.drbaxr.spektrum.flexible.adapters.CSModelAdapter
import me.drbaxr.spektrum.flexible.identifiers.rules.composite.And
import me.drbaxr.spektrum.flexible.identifiers.rules.composite.Or
import me.drbaxr.spektrum.flexible.identifiers.rules.cs.HasSomeAttribute
import me.drbaxr.spektrum.flexible.identifiers.rules.cs.HasSomeUsingStatements
import me.drbaxr.spektrum.flexible.identifiers.rules.general.ContainsTestInIdentifier
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue

class CombinationTest {

    @Before
    fun init() {
        CSModelAdapter("src/test/resources/inputs/honeydewCSModel_in.json").adapt()
    }

    @Test
    fun testCombination() {
        val identifier = "HoneydewCoreIntegrationTests/Processors/RepositoryModelToReferenceRepositoryModelProcessorTests.cs->" +
                "HoneydewCoreIntegrationTests.Processors->" +
                "RepositoryModelToReferenceRepositoryModelProcessorTests->" +
                "GetFunction_ShouldReturnEmptyProjects_WhenSolutionModelIsNull#"

        val unit = HierarchyMethod(
            identifier,
            mutableMapOf()
        )

        val trueRule1 = ContainsTestInIdentifier()
        val trueRule2 = HasSomeAttribute(setOf("Xunit.FactAttribute"))
        val falseRule = HasSomeUsingStatements(setOf("Nope.No"))

        val andRuleTrue = And(listOf(trueRule1, trueRule2))
        val orRuleFalse = Or(listOf(falseRule, falseRule, falseRule))
        val orRuleTrue = Or(listOf(falseRule, falseRule, falseRule, trueRule2))

        val compositeRule = Or(listOf(andRuleTrue, orRuleFalse, orRuleTrue))

        assertTrue { compositeRule.isRespectedBy(unit) }
    }

}