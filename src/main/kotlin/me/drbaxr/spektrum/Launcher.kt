package me.drbaxr.spektrum

import me.drbaxr.spektrum.fixed.central.CoverageModelCalculator
import me.drbaxr.spektrum.fixed.central.MetricsExporter
import me.drbaxr.spektrum.fixed.central.UnitClassifier
import me.drbaxr.spektrum.flexible.adapters.cs.CSModelAdapter
import me.drbaxr.spektrum.flexible.adapters.java.JavaModelAdapter
import me.drbaxr.spektrum.flexible.identifiers.TestIdentifier
import me.drbaxr.spektrum.flexible.identifiers.rules.cs.ScriptRuleCS
import me.drbaxr.spektrum.flexible.identifiers.rules.java.ScriptRuleJava
import java.util.logging.Level
import java.util.logging.Logger

class Launcher {
    private val logger = Logger.getLogger("LAUNCHER")

    enum class Language {
        CSHARP,
        JAVA
    }

    fun launch(language: Language, file: String) {
        logger.log(Level.INFO, "Creating adapter...")
        val adapter = when (language) {
            Language.CSHARP -> CSModelAdapter(file)
            Language.JAVA -> JavaModelAdapter(file)
        }

        logger.log(Level.INFO, "Creating identifier...")
        val identifier = when (language) {
            Language.CSHARP -> TestIdentifier(listOf(ScriptRuleCS("rules/cs/rule.groovy")))
            Language.JAVA -> TestIdentifier(listOf(ScriptRuleJava("rules/java/rule.groovy")))
        }

        logger.log(Level.INFO, "Adapting input model...")
        val model = adapter.adapt()

        logger.log(Level.INFO, "Classifying units...")
        UnitClassifier().classify(model, identifier)

        logger.log(Level.INFO, "Calculating metrics...")
        val coveredModel = CoverageModelCalculator().calculate(model)

        logger.log(Level.INFO, "Exporting model...")
        MetricsExporter().exportAndSave(coveredModel)

        logger.log(Level.INFO, "Model exported to ./analytics/model.json")
    }
}