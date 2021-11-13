package me.drbaxr.exception

class NonTestableUnitException(identifier: String) : Exception("$identifier is not a testable unit") {
}