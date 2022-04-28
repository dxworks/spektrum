package me.drbaxr.spektrum.flexible.adapters.java.model.external

data class MethodJava(
    val id: Long,
    val signature: String,
    val callers: Set<Long>,
    val calledMethods: Set<Long>
)
