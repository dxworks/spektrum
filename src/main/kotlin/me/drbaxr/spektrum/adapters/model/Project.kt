package me.drbaxr.spektrum.adapters.model

data class Project(
    val name: String,
    val path: String,
    val files: Set<File>,
    val projectReferences: Set<String>,
    val externalReferences: Set<String>
)
