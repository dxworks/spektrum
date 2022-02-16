package me.drbaxr.spektrum.test

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import me.drbaxr.spektrum.adapters.MethodTreeBuilder
import me.drbaxr.spektrum.adapters.model.external.ImportModel
import org.junit.Test
import org.junit.Before
import java.io.FileReader
import kotlin.test.assertEquals

class MethodTreeBuilderTest {
    private lateinit var model: ImportModel
    private lateinit var builder: MethodTreeBuilder

    @Before
    fun init() {
        model = getOriginalModel("inputs/Honeydew-testing_stuff.json")
        builder = MethodTreeBuilder(model)
    }

    @Test
    fun testBuild() {
        model.projects.forEach { project ->
            project.files.forEach { file ->
                file.namespaces.forEach { namespace ->
                    namespace.classes.forEach { cls ->
                        cls.methods.forEach { method ->
                            if (method.type == "Method") {
                                val className = cls.name.split(".").last()
                                val fullName = builder.fullName(file.path, namespace.name, className, method.name)
                                builder.build(fullName)
                            }
                        }
                    }
                }
            }
        }
    }

    @Test
    fun testFullName() {
        val expected = "file->namespace.class@method"
        val actual = builder.fullName("file", "namespace", "class", "method")
        assertEquals(expected, actual)
    }

    @Test
    fun testTrimNameNormal() {
        val expected = "name"
        val actual = builder.trimName("name#params")
        assertEquals(expected, actual)
    }

    @Test
    fun testTrimNameNOP() {
        val expected = "name"
        val actual = builder.trimName("name")
        assertEquals(expected, actual)
    }

    private fun getOriginalModel(file: String): ImportModel {
        val type = object : TypeToken<ImportModel>() {}.type
        return Gson().fromJson(FileReader(file), type)
    }
}