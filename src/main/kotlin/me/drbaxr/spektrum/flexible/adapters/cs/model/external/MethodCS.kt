package me.drbaxr.spektrum.flexible.adapters.cs.model.external

import me.drbaxr.spektrum.fixed.model.HierarchyUnit

data class MethodCS(
    val name: String, // <file>-><namespace>.<class>@<method>#<params>
    val attributes: Set<String>,
    val modifiers: Set<String>,
    val callers: Set<String>,
    val calledMethods: Set<String>,
    val type: String
) {
    companion object {
        fun file(fullName: String): String = fullName.split("->")[0]

        fun namespace(fullName: String): String {
            val split = getSplit(fullName)
            return split.subList(0, split.size - 1).joinToString(".")
        }

        fun className(fullName: String): String {
            val split = getSplit(fullName)
            return split.last()
        }

        fun method(fullName: String): String = fullName.split("@")[1]

        fun trimmedFullName(fullName: String) = fullName.split("#")[0]

        fun fullName(filePath: String, namespaceName: String, className: String, methodName: String): String =
            "${filePath}->${namespaceName}.${className}@${methodName}"

        fun methodNameToFullName(name: String): String {
            val splitName = name.split(HierarchyUnit.childSeparator)
            val file = splitName[0]
            val namespace = splitName[1]
            val cls = splitName[2].split(".").last()
            val method = splitName[3]
            return fullName(file, namespace, cls, method)
        }

        private fun getSplit(fullName: String): List<String> {
            val noFile = fullName.split("->")[1]
            val namespaceClass = noFile.split("@")[0]
            return namespaceClass.split(".")
        }
    }

    fun getInfo(): MethodCSInfo = MethodCSInfo(
        name,
        attributes,
        modifiers,
        callers,
        calledMethods,
        type
    )
}
