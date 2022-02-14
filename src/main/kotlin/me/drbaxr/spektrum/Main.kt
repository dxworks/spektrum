package me.drbaxr.spektrum

import me.drbaxr.spektrum.adapters.CSModelAdapter

fun main() {
    val adapter = CSModelAdapter()
    val model = adapter.adapt()

    model.forEach {
        println(it.toPrettyString())
    }
}