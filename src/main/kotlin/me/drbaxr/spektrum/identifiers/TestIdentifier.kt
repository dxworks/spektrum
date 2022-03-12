package me.drbaxr.spektrum.identifiers

import me.drbaxr.spektrum.identifiers.rules.Rule
import me.drbaxr.spektrum.main.model.HierarchyUnit

class TestIdentifier(private val rules: List<Rule>) {

    fun isTest(unit: HierarchyUnit): Boolean = rules.foldRight(true) { rule, acc ->
        acc && rule.isRespectedBy(unit)
    }

}