package me.drbaxr.spektrum.flexible.adapters.model.external

data class Class(
    val name: String, // NOTE: class name also contains namespace name - namespace.class
    val type: String,
    val usingStatements: Set<String>,
    val attributes: Set<String>,
    val usedClasses: Set<String>,
    val methods: Set<Method>
)
