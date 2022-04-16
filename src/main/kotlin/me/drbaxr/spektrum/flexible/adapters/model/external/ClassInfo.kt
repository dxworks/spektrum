package me.drbaxr.spektrum.flexible.adapters.model.external

data class ClassInfo(
    val name: String,
    val type: String,
    val usingStatements: Set<String>,
    val attributes: Set<String>,
    val usedClasses: Set<String>
)
