package me.drbaxr.spektrum.main.exception

class NonTestableUnitException(identifier: String) : Exception("$identifier is not a testable unit") {
}