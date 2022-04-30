package me.drbaxr.spektrum.flexible.identifiers.rules.java.exceptions

class PackageNotFoundException(identifier: String): Exception("No package with identifier $identifier")