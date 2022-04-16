package me.drbaxr.spektrum.flexible.adapters.model.external

import me.drbaxr.spektrum.flexible.adapters.model.external.NamespaceInfo

data class Namespace(
    val name: String,
    val classes: Set<Class>
) {
    fun getInfo(): NamespaceInfo = NamespaceInfo(name)
}
