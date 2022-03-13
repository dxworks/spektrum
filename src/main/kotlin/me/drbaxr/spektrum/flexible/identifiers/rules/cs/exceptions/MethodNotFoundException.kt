package me.drbaxr.spektrum.flexible.identifiers.rules.cs.exceptions

class MethodNotFoundException(identifier: String)
    : Exception("No method with identifier '$identifier' found in given model")