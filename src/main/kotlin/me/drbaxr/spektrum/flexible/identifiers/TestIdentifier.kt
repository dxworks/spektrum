package me.drbaxr.spektrum.flexible.identifiers

import me.drbaxr.spektrum.flexible.identifiers.rules.Rule
import me.drbaxr.spektrum.fixed.model.HierarchyUnit

class TestIdentifier(private val rules: List<Rule>) {

    fun isTest(unit: HierarchyUnit): Boolean = rules.foldRight(true) { rule, acc ->
        acc && rule.isRespectedBy(unit)
    }

}