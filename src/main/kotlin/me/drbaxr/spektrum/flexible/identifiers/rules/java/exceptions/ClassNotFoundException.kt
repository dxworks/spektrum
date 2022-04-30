package me.drbaxr.spektrum.flexible.identifiers.rules.java.exceptions

class ClassNotFoundException(identifier: String) : Exception("No class with identifier $identifier")