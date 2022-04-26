package me.drbaxr.spektrum.test.adapters

import me.drbaxr.spektrum.flexible.adapters.java.JavaModelAdapter
import org.junit.Test

class JavaModelAdapterTest {

    @Test
    fun testJavaModelAdapter() {
        val adapter = JavaModelAdapter("inputs/insider-tree-layout_java.json")

        val adaptedModel = adapter.adapt()

        adaptedModel.forEach {
            println(it.toPrettyString())
        }
    }
}