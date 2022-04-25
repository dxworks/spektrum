package me.drbaxr.spektrum.flexible.adapters.cs.model.external

data class MethodCSInfo(
    val name: String, // <file>-><namespace>.<class>@<method>#<params>
    val attributes: Set<String>,
    val modifiers: Set<String>,
    val callers: Set<String>,
    val calledMethods: Set<String>,
    val type: String
)
