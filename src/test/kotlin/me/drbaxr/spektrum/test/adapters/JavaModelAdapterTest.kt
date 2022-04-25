package me.drbaxr.spektrum.test.adapters

import me.drbaxr.spektrum.flexible.adapters.java.JavaModelAdapter
import org.junit.Test

class JavaModelAdapterTest {

    @Test
    fun test() {
        val adapter = JavaModelAdapter("inputs/insider-tree-layout_java.json")

        adapter.adapt()
    }
}