package me.drbaxr.spektrum.flexible.adapters.java.model.external

data class MethodJava(
    val id: Long,
    val signature: String,
    val callers: Set<Long>,
    val calledMethods: Set<Long>
) {
    fun getInfo(projectJava: ProjectJava): MethodJavaInfo {
        return MethodJavaInfo(
            signature,
            callers.map { projectJava.getMethodHierarchyName(it) }.toSet(),
            calledMethods.map { projectJava.getMethodHierarchyName(it) }.toSet()
        )
    }
}
