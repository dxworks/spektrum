package me.drbaxr.spektrum.flexible.adapters.java.model.external

data class PackageJava(
    val id: Int,
    val name: String,
    val classes: Set<ClassJava>
)
