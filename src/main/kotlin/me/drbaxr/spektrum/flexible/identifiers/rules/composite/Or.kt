package me.drbaxr.spektrum.flexible.identifiers.rules.composite

import me.drbaxr.spektrum.fixed.model.HierarchyUnit
import me.drbaxr.spektrum.flexible.identifiers.rules.Rule

class Or(val rules: List<Rule>) : Rule {

    override fun isRespectedBy(unit: HierarchyUnit): Boolean =
        rules.foldRight(false) { rule, acc -> acc || rule.isRespectedBy(unit) }

}