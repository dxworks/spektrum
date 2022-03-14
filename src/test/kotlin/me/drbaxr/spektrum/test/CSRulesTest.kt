package me.drbaxr.spektrum.test

import me.drbaxr.spektrum.fixed.model.HierarchyMethod
import me.drbaxr.spektrum.flexible.adapters.CSModelAdapter
import me.drbaxr.spektrum.flexible.identifiers.rules.cs.HasSomeAttribute
import me.drbaxr.spektrum.flexible.identifiers.rules.cs.HasSomeUsingStatements
import me.drbaxr.spektrum.flexible.identifiers.rules.cs.exceptions.*
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.BeforeTest
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CSRulesTest {

    @BeforeTest
    fun init() {
        CSModelAdapter("src/test/resources/inputs/honeydewCSModel_in.json").adapt()
    }

    @Test
    fun testHasSomeAttributeTrue() {
        val identifier = "HoneydewCoreIntegrationTests/Processors/RepositoryModelToReferenceRepositoryModelProcessorTests.cs->" +
                "HoneydewCoreIntegrationTests.Processors->" +
                "RepositoryModelToReferenceRepositoryModelProcessorTests->" +
                "GetFunction_ShouldReturnEmptyProjects_WhenSolutionModelIsNull#"

        val unit = HierarchyMethod(
            identifier,
            mutableMapOf()
        )

        assertTrue {
            HasSomeAttribute(setOf("Some.Random.Attribute", "Xunit.FactAttribute"))
                .isRespectedBy(unit)
        }
    }

    @Test
    fun testHasSomeAttributeFalse() {
        val identifier = "HoneydewCoreIntegrationTests/Processors/RepositoryModelToReferenceRepositoryModelProcessorTests.cs->" +
                "HoneydewCoreIntegrationTests.Processors->" +
                "RepositoryModelToReferenceRepositoryModelProcessorTests->" +
                "GetFunction_ShouldReturnEmptyProjects_WhenSolutionModelIsEmpty#"

        val unit = HierarchyMethod(
            identifier,
            mutableMapOf()
        )

        assertFalse {
            HasSomeAttribute(setOf("Some.Random.Attribute", "No", "Attribute.Here"))
                .isRespectedBy(unit)
        }
    }

    @Test
    fun `test should throw NotHierarchyMethodException`() {
        val identifier = "HoneydewCoreIntegrationTests/Processors/RepositoryModelToReferenceRepositoryModelProcessorTests.cs->" +
                "HoneydewCoreIntegrationTests.Processors->" +
                "RepositoryModelToReferenceRepositoryModelProcessorTests"

        val unit = HierarchyMethod(
            identifier,
            mutableMapOf()
        )

        assertThrows<NotHierarchyMethodException> {
            HasSomeAttribute(setOf("Some.Random.Attribute", "No", "Attribute.Here"))
                .isRespectedBy(unit)
        }
    }

    @Test
    fun `test should throw FileNotFoundException`() {
        val identifier = "path/does/not/exist.cs->" +
                "HoneydewCoreIntegrationTests.Processors->" +
                "RepositoryModelToReferenceRepositoryModelProcessorTests->" +
                "GetFunction_ShouldReturnEmptyProjects_WhenSolutionModelIsEmpty#"

        val unit = HierarchyMethod(
            identifier,
            mutableMapOf()
        )

        assertThrows<FileNotFoundException> {
            HasSomeAttribute(setOf("Some.Random.Attribute", "No", "Attribute.Here"))
                .isRespectedBy(unit)
        }
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

    @Test
    fun testHasSomeUsedClassTrue() {
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
    fun testHasSomeUsedClassFalse() {
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