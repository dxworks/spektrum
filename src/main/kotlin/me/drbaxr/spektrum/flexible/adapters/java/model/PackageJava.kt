package me.drbaxr.spektrum.flexible.adapters.java.model

data class PackageJava(
    val id: Int,
    val name: String,
    val classes: Set<ClassJava>
)
