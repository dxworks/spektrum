package me.drbaxr.spektrum.flexible.adapters.cs.model.external

data class ClassCS(
    val name: String, // NOTE: class name also contains namespace name - namespace.class
    val type: String,
    val usingStatements: Set<String>,
    val attributes: Set<String>,
    val usedClasses: Set<String>,
    val methods: Set<MethodCS>
) {
    fun getInfo(): ClassCSInfo = ClassCSInfo(
        name,
        type,
        usingStatements,
        attributes,
        usedClasses
    )
}
