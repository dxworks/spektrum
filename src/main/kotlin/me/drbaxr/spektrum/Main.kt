package me.drbaxr.spektrum

import java.util.logging.Level
import java.util.logging.Logger
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val logger = Logger.getLogger("Main")
    val languageMap = mapOf(
        "cs" to Launcher.Language.CSHARP,
        "java" to Launcher.Language.JAVA
    )

    if (args.size != 2) {
        logger.log(Level.SEVERE, "Usage: java -jar ./spektrum.jar <language> <file>")
        exitProcess(1)
    }

    // TODO: more error handling
    Launcher().launch(languageMap[args[0]]!!, args[1])
}