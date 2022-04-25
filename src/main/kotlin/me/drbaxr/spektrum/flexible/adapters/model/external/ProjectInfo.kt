package me.drbaxr.spektrum.flexible.adapters.model.external

data class ProjectInfo(
    val name: String,
    val path: String,
    val projectReferences: Set<String>,
    val externalReferences: Set<String>
)
