package me.drbaxr.spektrum.test.rules.script

import groovy.lang.GroovyShell
import org.junit.Test
import java.io.File

class ScriptRuleTest {

    @Test
    fun test() {
        GroovyShell()
            .parse(File("scripts/test.groovy"))
            .invokeMethod("hello_world", null)

        TODO("Rule that you can read from a groovy file")
    }
}