package me.drbaxr.spektrum.flexible.identifiers.rules.java.exceptions

class MethodNotFoundException(identifier: String) : Exception("No method with identifier $identifier")