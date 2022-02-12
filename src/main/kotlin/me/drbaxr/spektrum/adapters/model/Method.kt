package me.drbaxr.spektrum.adapters.model

data class Method(
    val name: String,
    val attributes: Set<String>,
    val modifiers: Set<String>,
    val callers: Set<String>,
    val calledMethods: Set<String>
)
