package me.drbaxr.spektrum.adapters.model.internal

data class MethodTreeNode(
    val identifier: String,
    val callerMethods: Set<MethodTreeNode>,
    val calledMethods: Set<MethodTreeNode>
)
