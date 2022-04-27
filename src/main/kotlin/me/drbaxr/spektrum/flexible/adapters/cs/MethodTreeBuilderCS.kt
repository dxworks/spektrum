package me.drbaxr.spektrum.flexible.adapters.cs

import me.drbaxr.spektrum.flexible.adapters.cs.model.external.FileCS
import me.drbaxr.spektrum.flexible.adapters.cs.model.external.ImportModelCS
import me.drbaxr.spektrum.flexible.adapters.cs.model.external.MethodCS
import me.drbaxr.spektrum.flexible.adapters.cs.model.internal.MethodTreeNodeCS
import kotlin.Exception

class MethodTreeBuilderCS(private val model: ImportModelCS) {
    // returns null if the searched method does not exist in model
    // ignoredCallers is the stack of method calls so far: first element is method that was initially called
    fun build(methodIdentifier: String, ignoredCallers: List<String>): MethodTreeNodeCS? {
        return try {
            val method = getMethod(methodIdentifier)

            val callerMethods = mutableSetOf<MethodTreeNodeCS>()
            method.callers.forEach { callerName ->
                if (!ignoredCallers.contains(callerName) && callerName != methodIdentifier) { // not called by itself
                    val newCallers = listOf(*ignoredCallers.toTypedArray(), callerName)
                    val callerNode =
                        build(callerName, newCallers)
                    if (callerNode != null)
                        callerMethods.add(callerNode)
                }
            }

            return MethodTreeNodeCS(methodIdentifier, callerMethods)
        } catch (e: Exception) {
            null
        }
    }

    private fun getMethod(methodIdentifier: String): MethodCS {
        val filePath = MethodCS.file(methodIdentifier)
        val namespaceName = MethodCS.namespace(methodIdentifier)
        val className = MethodCS.className(methodIdentifier)
        val methodName = MethodCS.method(methodIdentifier)

        var file: FileCS? = null
        model.projects.forEach { project ->
            project.files.forEach {
                if (it.path == filePath)
                    file = it
            }
        }

        val foundFile = file
            ?: throw Exception(getNotFoundExceptionText(methodIdentifier, "FILE"))
        val namespace = foundFile.namespaces.find { it.name == namespaceName }
            ?: throw Exception(getNotFoundExceptionText(methodIdentifier, "NAMESPACE"))
        val cls = namespace.classes.find { it.name == "$namespaceName.$className" }
            ?: throw Exception(getNotFoundExceptionText(methodIdentifier, "CLASS"))
        val methods = cls.methods
        return methods.find { it.name == methodName }
            ?: throw Exception(getNotFoundExceptionText(methodIdentifier, "METHOD"))
    }

    private fun getNotFoundExceptionText(name: String, type: String): String =
        "Method $name does not exist ($type not found)"
}