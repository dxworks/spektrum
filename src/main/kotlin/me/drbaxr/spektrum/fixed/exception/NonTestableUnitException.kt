package me.drbaxr.spektrum.fixed.exception

class NonTestableUnitException(identifier: String) : Exception("$identifier is not a testable unit") {
}