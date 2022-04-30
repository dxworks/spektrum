package me.drbaxr.spektrum.flexible.adapters.java.model.external

import me.drbaxr.spektrum.flexible.adapters.java.model.external.exceptions.JavaTreeMethodNotFoundException

data class MethodJava(
    val id: Long,
    val signature: String,
    val callers: Set<Long>,
    val calledMethods: Set<Long>
) {
    fun getInfo(projectJava: ProjectJava): MethodJavaInfo {
        return MethodJavaInfo(
            signature,
            callers.mapNotNull {
                try {
                    projectJava.getMethodHierarchyName(it)
                } catch (e: JavaTreeMethodNotFoundException) {
                    null
                }
            }.toSet(),
            calledMethods.mapNotNull {
                try {
                    projectJava.getMethodHierarchyName(it)
                } catch (e: JavaTreeMethodNotFoundException) {
                    null
                }
            }.toSet()
        )
    }
}
