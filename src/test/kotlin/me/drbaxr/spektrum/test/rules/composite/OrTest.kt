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
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class OrTest {

    @Before
    fun init() {
        CSModelAdapter("src/test/resources/inputs/honeydewCSModel_in.json").adapt()
    }

    @Test
    fun testOrTrue() {
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
        val compositeRule = Or(listOf(trueRule1, falseRule, trueRule2))

        assertTrue { compositeRule.isRespectedBy(unit) }
    }

    @Test
    fun testOrFalse() {
        val identifier = "HoneydewCoreIntegrationTests/Processors/RepositoryModelToReferenceRepositoryModelProcessorTests.cs->" +
                "HoneydewCoreIntegrationTests.Processors->" +
                "RepositoryModelToReferenceRepositoryModelProcessorTests->" +
                "GetFunction_ShouldReturnEmptyProjects_WhenSolutionModelIsNull#"

        val unit = HierarchyMethod(
            identifier,
            mutableMapOf()
        )

        val falseRule = HasSomeAttribute(setOf("Nope", "False"))
        val compositeRule = And(listOf(falseRule, falseRule, falseRule))

        assertFalse { compositeRule.isRespectedBy(unit) }
    }

}