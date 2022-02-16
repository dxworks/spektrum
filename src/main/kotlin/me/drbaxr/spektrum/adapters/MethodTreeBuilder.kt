package me.drbaxr.spektrum.adapters

import me.drbaxr.spektrum.adapters.model.external.File
import me.drbaxr.spektrum.adapters.model.external.ImportModel
import me.drbaxr.spektrum.adapters.model.external.Method
import me.drbaxr.spektrum.adapters.model.internal.MethodTreeNode
import kotlin.Exception

class MethodTreeBuilder(private val model: ImportModel) {
    // methodIdentifier needs to be full name - at the moment it does not use # part
    // returns null if the searched method does not exist in model
    fun build(methodIdentifier: String): MethodTreeNode? {
        return try {
            val method = getMethod(methodIdentifier)
            TODO("Construct MethodTree and return node of searched method")

            MethodTreeNode("", setOf(), setOf())
        } catch (e: Exception) {
            null
        }
    }

    fun fullName(filePath: String, namespaceName: String, className: String, methodName: String): String =
        "${filePath}->${namespaceName}.${className}@${methodName}"

    fun trimName(methodName: String): String = methodName.split("#")[0]

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