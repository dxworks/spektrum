package me.drbaxr.spektrum.fixed.exception

@Deprecated("no linger needed")
class NonTestableUnitException(identifier: String) : Exception("$identifier is not a testable unit") {
}