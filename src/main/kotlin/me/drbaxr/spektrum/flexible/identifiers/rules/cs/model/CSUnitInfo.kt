package me.drbaxr.spektrum.flexible.identifiers.rules.cs.model

import me.drbaxr.spektrum.flexible.adapters.cs.model.external.*

data class CSUnitInfo(
    val project: ProjectCSInfo,
    val file: FileCSInfo,
    val namespace: NamespaceCSInfo,
    val cls: ClassCSInfo,
    val method: MethodCSInfo
)
