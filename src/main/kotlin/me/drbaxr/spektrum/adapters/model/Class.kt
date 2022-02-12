package me.drbaxr.spektrum.adapters.model

data class Class(
    val name: String,
    val type: String,
    val usingStatements: Set<String>,
    val attributes: Set<String>,
    val usedClasses: Set<String>,
    val methods: Set<Method>
)
