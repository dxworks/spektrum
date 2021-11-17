package me.drbaxr.model

data class ExportUnit (
    val identifier: String,
    val type: String,
    val coverage: Float,
    val children: MutableSet<ExportUnit>?
)