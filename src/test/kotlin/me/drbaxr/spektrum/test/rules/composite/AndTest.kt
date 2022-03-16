package me.drbaxr.spektrum.test.rules.composite

import me.drbaxr.spektrum.fixed.model.HierarchyMethod
import me.drbaxr.spektrum.flexible.adapters.CSModelAdapter
import me.drbaxr.spektrum.flexible.identifiers.rules.composite.And
import me.drbaxr.spektrum.flexible.identifiers.rules.cs.HasSomeAttribute
import me.drbaxr.spektrum.flexible.identifiers.rules.general.ContainsTestInIdentifier
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class AndTest {

    @Before
    fun init() {
        CSModelAdapter("src/test/resources/inputs/honeydewCSModel_in.json").adapt()
    }

    @Test
    fun testAndTrue() {
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
        val compositeRule = And(listOf(trueRule1, trueRule2))

        assertTrue { compositeRule.isRespectedBy(unit) }
    }

    @Test
    fun testAndFalse() {
        val identifier = "HoneydewCoreIntegrationTests/Processors/RepositoryModelToReferenceRepositoryModelProcessorTests.cs->" +
                "HoneydewCoreIntegrationTests.Processors->" +
                "RepositoryModelToReferenceRepositoryModelProcessorTests->" +
                "GetFunction_ShouldReturnEmptyProjects_WhenSolutionModelIsNull#"

        val unit = HierarchyMethod(
            identifier,
            mutableMapOf()
        )

        val trueRule = ContainsTestInIdentifier()
        val falseRule = HasSomeAttribute(setOf("Nope", "False"))
        val compositeRule = And(listOf(trueRule, falseRule))

        assertFalse { compositeRule.isRespectedBy(unit) }
    }

}