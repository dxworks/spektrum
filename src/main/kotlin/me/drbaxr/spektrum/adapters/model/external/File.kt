package me.drbaxr.spektrum.adapters.model.external

data class File(
    val name: String,
    val path: String,
    val namespaces: Set<Namespace>
)
