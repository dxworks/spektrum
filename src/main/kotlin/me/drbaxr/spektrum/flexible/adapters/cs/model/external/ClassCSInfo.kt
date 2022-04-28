package me.drbaxr.spektrum.flexible.adapters.cs.model.external

data class ClassCSInfo(
    val name: String,
    val type: String,
    val usingStatements: Set<String>,
    val attributes: Set<String>,
    val usedClasses: Set<String>
)
