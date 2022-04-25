package me.drbaxr.spektrum.flexible.adapters.java.model

data class MethodJava(
    val id: Int,
    val signature: String,
    val callers: Set<Int>,
    val calledMethods: Set<Int>
)
