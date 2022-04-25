package me.drbaxr.spektrum.flexible.adapters.model.external

data class MethodInfo(
    val name: String, // <file>-><namespace>.<class>@<method>#<params>
    val attributes: Set<String>,
    val modifiers: Set<String>,
    val callers: Set<String>,
    val calledMethods: Set<String>,
    val type: String
)
