package me.drbaxr.spektrum.test

import me.drbaxr.spektrum.identifiers.rules.ContainsTestInIdentifier
import me.drbaxr.spektrum.main.model.HierarchyUnit
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class RulesTest {
    @Test
    fun testContainsTestInIdentifierTrue() {
        val unit = HierarchyUnit(
            "someTestUnit",
            mutableSetOf(),
            "METHOD"
        )

        assertTrue { ContainsTestInIdentifier().isRespectedBy(unit) }
    }

    @Test
    fun testContainsTestInIdentifierFalse() {
        val unit = HierarchyUnit(
            "notTstUnit",
            mutableSetOf(),
            "METHOD"
        )

        assertFalse { ContainsTestInIdentifier().isRespectedBy(unit) }
    }
}