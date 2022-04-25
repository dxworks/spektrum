package me.drbaxr.spektrum.flexible.identifiers.rules.cs

import me.drbaxr.spektrum.fixed.model.HierarchyUnit
import me.drbaxr.spektrum.flexible.RelevantInformation
import me.drbaxr.spektrum.flexible.identifiers.rules.Rule

class HasSomeAttribute(val attributes: Set<String>): Rule {

    override fun isRespectedBy(unit: HierarchyUnit): Boolean {
        val unitInfo = RelevantInformation.getCSImportModelInformation(unit.identifier)

        return attributes.any { attribute -> unitInfo.method.attributes.any { it == attribute } }
    }

}