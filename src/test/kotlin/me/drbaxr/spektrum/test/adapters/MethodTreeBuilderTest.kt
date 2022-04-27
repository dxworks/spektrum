package me.drbaxr.spektrum.test.adapters

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import me.drbaxr.spektrum.flexible.adapters.cs.MethodTreeBuilderCS
import me.drbaxr.spektrum.flexible.adapters.cs.model.external.ImportModelCS
import me.drbaxr.spektrum.flexible.adapters.cs.model.external.MethodCS
import me.drbaxr.spektrum.flexible.adapters.cs.model.internal.MethodTreeNodeCS
import me.drbaxr.spektrum.flexible.adapters.java.MethodTreeBuilderJava
import me.drbaxr.spektrum.flexible.adapters.java.model.external.ProjectJava
import me.drbaxr.spektrum.flexible.adapters.java.model.internal.MethodTreeNodeJava
import org.junit.Test
import org.junit.Before
import java.io.FileReader
import kotlin.test.assertEquals

class MethodTreeBuilderTest {
    private lateinit var modelCS: ImportModelCS
    private lateinit var builderCS: MethodTreeBuilderCS

    private lateinit var projectJava: ProjectJava
    private lateinit var builderJava: MethodTreeBuilderJava

    @Before
    fun init() {
        modelCS = getOriginalModel("inputs/Honeydew-testing_stuff.json")
        builderCS = MethodTreeBuilderCS(modelCS)

        projectJava = Gson().fromJson(FileReader("inputs/insider-tree-layout_java.json"), ProjectJava::class.java)
        builderJava = MethodTreeBuilderJava(projectJava)
    }

    @Test
    fun testBuildCS() {
        val nodes = mutableListOf<MethodTreeNodeCS>()

        modelCS.projects.forEach { project ->
            project.files.forEach { file ->
                file.namespaces.forEach { namespace ->
                    namespace.classes.forEach { cls ->
                        cls.methods.forEach { method ->
                            if (method.type == "Method") {
                                val className = cls.name.split(".").last()
                                val fullName = MethodCS.fullName(file.path, namespace.name, className, method.name)
                                val node = builderCS.build(fullName, listOf())
                                if (node != null && node.callerMethods.isNotEmpty()) {
                                    nodes.add(node)
                                }
                            }
                        }
                    }
                }
            }
        }

        // TODO: make test do something smarter
        assertEquals(nodes.size, 101)
    }

    @Test
    fun testFullName() {
        val expected = "file->namespace.class@method"
        val actual = MethodCS.fullName("file", "namespace", "class", "method")
        assertEquals(expected, actual)
    }

    @Test
    fun testBuildJava() {
        val nodes = mutableListOf<MethodTreeNodeJava>()

        projectJava.packages.forEach { pkg ->
            pkg.classes.forEach { cls ->
                cls.methods.forEach { mtd ->
                    val node = builderJava.build(mtd.id, listOf())
                    if (node.callerMethods.isNotEmpty())
                        nodes.add(node)
                }
            }
        }

        // TODO: make test do something smarter
        assertEquals(nodes.size, 48)
    }

    private fun getOriginalModel(file: String): ImportModelCS {
        val type = object : TypeToken<ImportModelCS>() {}.type
        return Gson().fromJson(FileReader(file), type)
    }
}