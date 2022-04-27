package me.drbaxr.spektrum.flexible.adapters.java.model.external

data class ClassJava(
    val id: Int,
    val name: String,
    val dependencies: Set<String>,
    val methods: Set<MethodJava>
)
