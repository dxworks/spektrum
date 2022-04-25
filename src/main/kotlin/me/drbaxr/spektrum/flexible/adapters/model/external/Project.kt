package me.drbaxr.spektrum.flexible.adapters.model.external

import me.drbaxr.spektrum.flexible.adapters.model.external.ProjectInfo

data class Project(
    val name: String,
    val path: String,
    val files: Set<File>,
    val projectReferences: Set<String>,
    val externalReferences: Set<String>
) {
    fun getInfo(): ProjectInfo = ProjectInfo(
        name,
        path,
        projectReferences,
        externalReferences
    )
}
