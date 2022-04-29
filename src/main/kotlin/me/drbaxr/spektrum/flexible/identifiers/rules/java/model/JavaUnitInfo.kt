package me.drbaxr.spektrum.flexible.identifiers.rules.java.model

import me.drbaxr.spektrum.flexible.adapters.java.model.external.ClassJavaInfo
import me.drbaxr.spektrum.flexible.adapters.java.model.external.MethodJavaInfo
import me.drbaxr.spektrum.flexible.adapters.java.model.external.PackageJavaInfo
import me.drbaxr.spektrum.flexible.adapters.java.model.external.ProjectJavaInfo

data class JavaUnitInfo(
    val project: ProjectJavaInfo,
    val pkg: PackageJavaInfo,
    val cls: ClassJavaInfo,
    val method: MethodJavaInfo
)
