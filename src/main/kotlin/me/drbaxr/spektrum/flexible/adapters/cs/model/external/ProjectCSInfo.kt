package me.drbaxr.spektrum.flexible.adapters.cs.model.external

data class ProjectCSInfo(
    val name: String,
    val path: String,
    val projectReferences: Set<String>,
    val externalReferences: Set<String>
)
