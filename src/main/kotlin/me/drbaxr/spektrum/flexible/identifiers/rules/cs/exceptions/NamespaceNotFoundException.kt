package me.drbaxr.spektrum.flexible.identifiers.rules.cs.exceptions

class NamespaceNotFoundException(identifier: String) :
    Exception("No namespace with identifier '$identifier' found in given model")