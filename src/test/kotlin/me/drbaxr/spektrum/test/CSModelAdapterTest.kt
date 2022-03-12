package me.drbaxr.spektrum.test

import me.drbaxr.spektrum.flexible.adapters.CSModelAdapter
import me.drbaxr.spektrum.test.util.TestUtil
import org.junit.Test
import java.io.FileReader
import kotlin.test.assertTrue

class CSModelAdapterTest {
    @Test
    fun testCSModel() {
        val adapter = CSModelAdapter("src/test/resources/inputs/honeydewCSModel_in.json")
        val actual = adapter.adapt()
        val expected = TestUtil.getMock(FileReader("src/test/resources/outputs/honeydewCSModel_exp.json"))

        var eq = true
        expected.forEach { exp ->
            val act = actual.find { it.identifier == exp.identifier }
            if (act?.isEqual(exp) != true)
                eq = false
        }
        assertTrue { eq }
    }
}