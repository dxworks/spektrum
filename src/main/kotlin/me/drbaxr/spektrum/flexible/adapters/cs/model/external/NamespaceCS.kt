package me.drbaxr.spektrum.flexible.adapters.cs.model.external

data class NamespaceCS(
    val name: String,
    val classes: Set<ClassCS>
) {
    fun getInfo(): NamespaceCSInfo = NamespaceCSInfo(name)
}
