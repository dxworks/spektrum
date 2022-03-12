package me.drbaxr.spektrum.fixed.model

data class ExportUnit (
    val identifier: String,
    val type: String,
    val coverage: Float,
    val children: MutableSet<ExportUnit>?
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ExportUnit

        if (identifier != other.identifier) return false
        if (type != other.type) return false
        if (coverage != other.coverage) return false
        if (children != other.children) return false

        return true
    }

    override fun hashCode(): Int {
        var result = identifier.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + coverage.hashCode()
        result = 31 * result + (children?.hashCode() ?: 0)
        return result
    }
}