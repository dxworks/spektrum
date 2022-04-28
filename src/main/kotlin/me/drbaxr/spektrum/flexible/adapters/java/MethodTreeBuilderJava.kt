package me.drbaxr.spektrum.flexible.adapters.java

import me.drbaxr.spektrum.flexible.adapters.java.model.external.ProjectJava
import me.drbaxr.spektrum.flexible.adapters.java.model.internal.MethodTreeNodeJava

class MethodTreeBuilderJava(private val project: ProjectJava) {
    // IMPORTANT: methodId MUST be id of a method, not of packages or classes
    fun build(methodId: Long, ignoredCallers: List<Long>): MethodTreeNodeJava {
        val method = project.getMethodById(methodId)

        val callerMethods = mutableSetOf<MethodTreeNodeJava>()
        method.callers.forEach { callerId ->
            if (!ignoredCallers.contains(callerId) && callerId != methodId) { // prevents loops in the tree from recursivity
                val newCallers = listOf(*ignoredCallers.toTypedArray(), callerId)
                val callerNode = build(callerId, newCallers)
                callerMethods.add(callerNode)
            }
        }

        return MethodTreeNodeJava(method.id, callerMethods)
    }
}