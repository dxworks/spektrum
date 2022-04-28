package me.drbaxr.spektrum

import me.drbaxr.spektrum.flexible.adapters.cs.CSModelAdapter

fun main() {
    val adapter = CSModelAdapter("inputs/Honeydew-testing_stuff.json")
    val model = adapter.adapt()

    model.forEach {
        println(it.toPrettyString())
    }
}