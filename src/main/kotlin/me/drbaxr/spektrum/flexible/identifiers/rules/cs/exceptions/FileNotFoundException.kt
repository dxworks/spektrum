package me.drbaxr.spektrum.flexible.identifiers.rules.cs.exceptions

class FileNotFoundException(identifier: String) :
    Exception("No file with identifier '$identifier' found in given model")