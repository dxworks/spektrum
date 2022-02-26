package me.drbaxr.spektrum.adapters.model.external

data class Method(
    val name: String, // <file>-><namespace>.<class>@<method>#<params>
    val attributes: Set<String>,
    val modifiers: Set<String>,
    val callers: Set<String>,
    val calledMethods: Set<String>,
    val type: String
) {
    companion object {
        fun file(name: String): String = name.split("->")[0]

        fun namespace(name: String): String {
            val split = getSplit(name)
            return split.subList(0, split.size - 1).joinToString(".")
        }

        fun className(name: String): String {
            val split = getSplit(name)
            return split.last()
        }

        fun method(name: String): String = name.split("@")[1]

        fun trimmedIdentifier(identifier: String) = identifier.split("#")[0]

        private fun getSplit(name: String): List<String> {
            val noFile = name.split("->")[1]
            val namespaceClass = noFile.split("@")[0]
            return namespaceClass.split(".")
        }
    }
}
