package me.drbaxr.spektrum.flexible.adapters.cs.model.external

data class ProjectCS(
    val name: String,
    val path: String,
    val files: Set<FileCS>,
    val projectReferences: Set<String>,
    val externalReferences: Set<String>
) {
    fun getInfo(): ProjectCSInfo = ProjectCSInfo(
        name,
        path,
        projectReferences,
        externalReferences
    )
}
