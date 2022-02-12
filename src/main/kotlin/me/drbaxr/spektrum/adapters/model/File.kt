package me.drbaxr.spektrum.adapters.model

data class File(
    val name: String,
    val path: String,
    val namespaces: Set<Namespace>
)
