package me.drbaxr.spektrum.adapters

import me.drbaxr.spektrum.adapters.model.external.File
import me.drbaxr.spektrum.adapters.model.external.ImportModel
import me.drbaxr.spektrum.adapters.model.external.Method
import me.drbaxr.spektrum.adapters.model.internal.MethodTreeNode
import kotlin.Exception

class MethodTreeBuilder(private val model: ImportModel) {
    // returns null if the searched method does not exist in model
    // ignoredCallers is the stack of method calls so far: first element is method that was initially called
    fun build(methodIdentifier: String, ignoredCallers: List<String>): MethodTreeNode? {
        return try {
            val method = getMethod(methodIdentifier)

            val callerMethods = mutableSetOf<MethodTreeNode>()
            method.callers.forEach { callerName ->
                if (!ignoredCallers.contains(callerName) && callerName != methodIdentifier) { // not called by itself
                    val newCallers = listOf(*ignoredCallers.toTypedArray(), callerName)
                    val callerNode =
                        build(callerName, newCallers)
                    if (callerNode != null)
                        callerMethods.add(callerNode)
                }
            }

            return MethodTreeNode(methodIdentifier, callerMethods)
        } catch (e: Exception) {
            null
        }
    }

    fun fullName(filePath: String, namespaceName: String, className: String, methodName: String): String =
        "${filePath}->${namespaceName}.${className}@${methodName}"

    private fun getMethod(methodIdentifier: String): Method {
        val filePath = Method.file(methodIdentifier)
        val namespaceName = Method.namespace(methodIdentifier)
        val className = Method.className(methodIdentifier)
        val methodName = Method.method(methodIdentifier)

        var file: File? = null
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
        return cls.methods.find { it.name == methodName }
            ?: throw Exception(getNotFoundExceptionText(methodIdentifier, "METHOD"))
    }

    private fun getNotFoundExceptionText(name: String, type: String): String =
        "Method $name does not exist ($type not found)"
}