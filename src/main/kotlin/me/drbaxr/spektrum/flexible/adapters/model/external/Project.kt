package me.drbaxr.spektrum.flexible.adapters.model.external

data class Project(
    val name: String,
    val path: String,
    val files: Set<File>,
    val projectReferences: Set<String>,
    val externalReferences: Set<String>
)
