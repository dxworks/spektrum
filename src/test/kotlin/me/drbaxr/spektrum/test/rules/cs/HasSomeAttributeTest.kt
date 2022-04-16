package me.drbaxr.spektrum.test.rules.cs

import me.drbaxr.spektrum.fixed.model.HierarchyMethod
import me.drbaxr.spektrum.flexible.adapters.CSModelAdapter
import me.drbaxr.spektrum.flexible.identifiers.rules.cs.HasSomeAttribute
import me.drbaxr.spektrum.flexible.identifiers.rules.cs.exceptions.*
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class HasSomeAttributeTest {

    @Before
    fun init() {
        CSModelAdapter("src/test/resources/inputs/honeydewCSModel_in.json").adapt()
    }

    @Test
    fun testHasSomeAttributeTrue() {
        val identifier = "HoneydewCoreTest/IO/Writers/Exporters/ExportUtilsTests.cs->" +
                "HoneydewCoreTest.IO.Writers.Exporters->" +
                "ExportUtilsTests->" +
                "CsvCountPerLine_ShouldReturn0_WhenNoNumberIsDetected#"

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
        val identifier = "HoneydewCoreTest/IO/Writers/Exporters/ExportUtilsTests.cs->" +
                "HoneydewCoreTest.IO.Writers.Exporters->" +
                "ExportUtilsTests->" +
                "CsvCountPerLine_ShouldReturn0_WhenNoNumberIsDetected#"

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

}