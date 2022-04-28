package me.drbaxr.spektrum.test.adapters

import me.drbaxr.spektrum.flexible.adapters.java.JavaModelAdapter
import me.drbaxr.spektrum.test.util.TestUtil
import org.junit.Test
import java.io.FileReader
import kotlin.test.assertTrue

class JavaModelAdapterTest {

    @Test
    fun testJavaModelAdapter() {
        val adapter = JavaModelAdapter("src/test/resources/inputs/jafaxInsiderJavaModel_in.json")

        val adaptedModel = adapter.adapt()

        val expected = TestUtil.getMock(FileReader("src/test/resources/outputs/jafaxInsiderJavaModel_exp.json"))

        var eq = true
        expected.forEach { exp ->
            val act = adaptedModel.find { it.identifier == exp.identifier }
            if (act?.isEqual(exp) != true)
                eq = false
        }

        assertTrue { eq }
    }
}