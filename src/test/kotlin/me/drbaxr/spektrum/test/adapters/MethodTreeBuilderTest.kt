package me.drbaxr.spektrum.test.adapters

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import me.drbaxr.spektrum.flexible.adapters.cs.MethodTreeBuilderCS
import me.drbaxr.spektrum.flexible.adapters.cs.model.external.ImportModelCS
import me.drbaxr.spektrum.flexible.adapters.cs.model.external.MethodCS
import me.drbaxr.spektrum.flexible.adapters.cs.model.internal.MethodTreeNodeCS
import org.junit.Test
import org.junit.Before
import java.io.FileReader
import kotlin.test.assertEquals

class MethodTreeBuilderTest {
    private lateinit var model: ImportModelCS
    private lateinit var builder: MethodTreeBuilderCS

    @Before
    fun init() {
        model = getOriginalModel("inputs/Honeydew-testing_stuff.json")
        builder = MethodTreeBuilderCS(model)
    }

    @Test
    fun testBuild() {
        val nodes = mutableListOf<MethodTreeNodeCS>()

        model.projects.forEach { project ->
            project.files.forEach { file ->
                file.namespaces.forEach { namespace ->
                    namespace.classes.forEach { cls ->
                        cls.methods.forEach { method ->
                            if (method.type == "Method") {
                                val className = cls.name.split(".").last()
                                val fullName = MethodCS.fullName(file.path, namespace.name, className, method.name)
                                val node = builder.build(fullName, listOf())
                                if (node != null && node.callerMethods.isNotEmpty()) {
                                    nodes.add(node)
                                }
                            }
                        }
                    }
                }
            }
        }

        nodes.forEach {
            println(it.toOrderString())
        }
    }

    @Test
    fun testFullName() {
        val expected = "file->namespace.class@method"
        val actual = MethodCS.fullName("file", "namespace", "class", "method")
        assertEquals(expected, actual)
    }

    private fun getOriginalModel(file: String): ImportModelCS {
        val type = object : TypeToken<ImportModelCS>() {}.type
        return Gson().fromJson(FileReader(file), type)
    }
}