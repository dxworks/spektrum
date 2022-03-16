package me.drbaxr.spektrum.test.rules.cs

import me.drbaxr.spektrum.fixed.model.HierarchyMethod
import me.drbaxr.spektrum.flexible.adapters.CSModelAdapter
import me.drbaxr.spektrum.flexible.identifiers.rules.cs.HasSomeAttribute
import me.drbaxr.spektrum.flexible.identifiers.rules.cs.exceptions.ClassNotFoundException
import me.drbaxr.spektrum.flexible.identifiers.rules.cs.exceptions.MethodNotFoundException
import me.drbaxr.spektrum.flexible.identifiers.rules.cs.exceptions.NamespaceNotFoundException
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertThrows

class HasSomeAttributeExceptionsTest {

    @Before
    fun init() {
        CSModelAdapter("src/test/resources/inputs/honeydewCSModel_in.json").adapt()
    }

    @Test
    fun `test should throw NamespaceNotFoundException`() {
        val identifier = "HoneydewCoreIntegrationTests/Processors/RepositoryModelToReferenceRepositoryModelProcessorTests.cs->" +
                "Invalid.Namespace->" +
                "RepositoryModelToReferenceRepositoryModelProcessorTests->" +
                "GetFunction_ShouldReturnEmptyProjects_WhenSolutionModelIsEmpty#"

        val unit = HierarchyMethod(
            identifier,
            mutableMapOf()
        )

        assertThrows<NamespaceNotFoundException> {
            HasSomeAttribute(setOf("Some.Random.Attribute", "No", "Attribute.Here"))
                .isRespectedBy(unit)
        }
    }

    @Test
    fun `test should throw ClassNotFoundException`() {
        val identifier = "HoneydewCoreIntegrationTests/Processors/RepositoryModelToReferenceRepositoryModelProcessorTests.cs->" +
                "HoneydewCoreIntegrationTests.Processors->" +
                "InvalidClass->" +
                "GetFunction_ShouldReturnEmptyProjects_WhenSolutionModelIsEmpty#"

        val unit = HierarchyMethod(
            identifier,
            mutableMapOf()
        )

        assertThrows<ClassNotFoundException> {
            HasSomeAttribute(setOf("Some.Random.Attribute", "No", "Attribute.Here"))
                .isRespectedBy(unit)
        }
    }

    @Test
    fun `test should throw MethodNotFoundException`() {
        val identifier = "HoneydewCoreIntegrationTests/Processors/RepositoryModelToReferenceRepositoryModelProcessorTests.cs->" +
                "HoneydewCoreIntegrationTests.Processors->" +
                "RepositoryModelToReferenceRepositoryModelProcessorTests->" +
                "invalidMethod#"

        val unit = HierarchyMethod(
            identifier,
            mutableMapOf()
        )

        assertThrows<MethodNotFoundException> {
            HasSomeAttribute(setOf("Some.Random.Attribute", "No", "Attribute.Here"))
                .isRespectedBy(unit)
        }
    }

}