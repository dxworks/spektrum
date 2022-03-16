package me.drbaxr.spektrum.test.rules.cs

import me.drbaxr.spektrum.fixed.model.HierarchyMethod
import me.drbaxr.spektrum.flexible.adapters.CSModelAdapter
import me.drbaxr.spektrum.flexible.identifiers.rules.cs.HasSomeUsingStatements
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class HasSomeUsingStatementsTest {

    @Before
    fun init() {
        CSModelAdapter("src/test/resources/inputs/honeydewCSModel_in.json").adapt()
    }

    @Test
    fun testHasSomeUsingStatementsTrue() {
        val identifier = "HoneydewCoreIntegrationTests/Processors/RepositoryModelToReferenceRepositoryModelProcessorTests.cs->" +
                "HoneydewCoreIntegrationTests.Processors->" +
                "RepositoryModelToReferenceRepositoryModelProcessorTests->" +
                "GetFunction_ShouldReturnEmptyProjects_WhenSolutionModelIsNull#"

        val unit = HierarchyMethod(
            identifier,
            mutableMapOf()
        )

        assertTrue {
            HasSomeUsingStatements(setOf("Some.Random.Attribute", "Xunit", "HoneydewModels.Reference"))
                .isRespectedBy(unit)
        }
    }

    @Test
    fun testHasSomeUsingStatementsFalse() {
        val identifier = "HoneydewCoreIntegrationTests/Processors/RepositoryModelToReferenceRepositoryModelProcessorTests.cs->" +
                "HoneydewCoreIntegrationTests.Processors->" +
                "RepositoryModelToReferenceRepositoryModelProcessorTests->" +
                "GetFunction_ShouldReturnEmptyProjects_WhenSolutionModelIsNull#"

        val unit = HierarchyMethod(
            identifier,
            mutableMapOf()
        )

        assertFalse {
            HasSomeUsingStatements(setOf("Some.Random.Attribute", "HoneydewModels.Reference"))
                .isRespectedBy(unit)
        }
    }

}