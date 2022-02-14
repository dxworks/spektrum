package me.drbaxr.spektrum.adapters.model

data class Method(
    val name: String, // <file>-><namespace>.<class>@<method>#<params>
    val attributes: Set<String>,
    val modifiers: Set<String>,
    val callers: Set<String>,
    val calledMethods: Set<String>
)
