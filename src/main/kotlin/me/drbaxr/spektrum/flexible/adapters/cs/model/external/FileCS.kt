package me.drbaxr.spektrum.flexible.adapters.cs.model.external

data class FileCS(
    val name: String,
    val path: String,
    val namespaces: Set<NamespaceCS>
) {
    fun getInfo(): FileCSInfo = FileCSInfo(
        name,
        path
    )
}
