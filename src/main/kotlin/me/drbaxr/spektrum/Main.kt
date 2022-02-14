package me.drbaxr.spektrum

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import me.drbaxr.spektrum.adapters.CSModelAdapter
import me.drbaxr.spektrum.main.mock.MockHierarchyUnit
import me.drbaxr.spektrum.main.model.HierarchyUnit
import me.drbaxr.spektrum.main.model.HierarchyMethod
import java.io.FileReader

fun main() {
    val adapter = CSModelAdapter()
    val model = adapter.adapt()

    model.forEach {
        println(it.toPrettyString())
    }
}