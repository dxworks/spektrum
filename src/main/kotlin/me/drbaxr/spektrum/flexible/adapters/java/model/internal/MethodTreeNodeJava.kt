package me.drbaxr.spektrum.flexible.adapters.java.model.internal

data class MethodTreeNodeJava(
    val id: Long,
    val callerMethods: Set<MethodTreeNodeJava>
)