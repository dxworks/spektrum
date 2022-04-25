package me.drbaxr.spektrum.flexible.identifiers.rules.cs.model

import me.drbaxr.spektrum.flexible.adapters.model.external.*

data class CSUnitInfo(
    val project: ProjectInfo,
    val file: FileInfo,
    val namespace: NamespaceInfo,
    val cls: ClassInfo,
    val method: MethodInfo
)
