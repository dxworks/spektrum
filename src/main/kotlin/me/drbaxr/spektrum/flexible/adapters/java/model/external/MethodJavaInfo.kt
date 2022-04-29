package me.drbaxr.spektrum.flexible.adapters.java.model.external

data class MethodJavaInfo(
    val signature: String,
    val callers: Set<String>,
    val calledMethods: Set<String>
)
