package me.drbaxr.spektrum.flexible.identifiers.rules

import me.drbaxr.spektrum.fixed.model.HierarchyUnit

interface Rule {
    companion object {
        const val scriptRuleEntryMethodName = "check"
    }

    fun isRespectedBy(unit: HierarchyUnit): Boolean
}