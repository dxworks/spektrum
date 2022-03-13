package me.drbaxr.spektrum.flexible.identifiers.rules.cs.exceptions

class NotHierarchyMethodException(identifier: String): Exception("Unit with identifier '$identifier' is not a method")