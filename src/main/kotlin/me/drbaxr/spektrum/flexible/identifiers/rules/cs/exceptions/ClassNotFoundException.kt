package me.drbaxr.spektrum.flexible.identifiers.rules.cs.exceptions

class ClassNotFoundException(identifier: String) :
    Exception("No class with identifier '$identifier' found in given model")