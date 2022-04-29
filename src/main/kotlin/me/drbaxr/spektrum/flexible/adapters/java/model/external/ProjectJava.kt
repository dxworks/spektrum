package me.drbaxr.spektrum.flexible.adapters.java.model.external

import me.drbaxr.spektrum.fixed.model.HierarchyUnit
import me.drbaxr.spektrum.flexible.adapters.java.model.external.exceptions.JavaTreeMethodNotFoundException

data class ProjectJava(
    val name: String,
    val packages: Set<PackageJava>
) {
    fun getInfo(): ProjectJavaInfo = ProjectJavaInfo(name)

    fun getMethodById(methodId: Long): MethodJava {
        var method: MethodJava? = null

        for (pkg in packages) {
            if (method != null) break

            for (cls in pkg.classes) {
                if (method != null) break

                for (mtd in cls.methods) {
                    if (mtd.id == methodId) {
                        method = mtd
                        break
                    }
                }
            }
        }

        return method ?: throw JavaTreeMethodNotFoundException(methodId)
    }

    fun getMethodHierarchyName(methodId: Long): String {
        for (pkg in packages) {
            for (cls in pkg.classes) {
                for (mtd in cls.methods) {
                    if (mtd.id == methodId) {
                        return "$name${HierarchyUnit.childSeparator}" +
                                "${pkg.name}${HierarchyUnit.childSeparator}" +
                                "${cls.name}${HierarchyUnit.childSeparator}" +
                                mtd.signature
                    }
                }
            }
        }

        throw JavaTreeMethodNotFoundException(methodId)
    }
}