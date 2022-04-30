package me.drbaxr.spektrum.flexible.adapters.java.model.external

data class PackageJava(
    val id: Long,
    val name: String,
    val classes: Set<ClassJava>
) {
    fun getInfo(): PackageJavaInfo = PackageJavaInfo(name)
}
