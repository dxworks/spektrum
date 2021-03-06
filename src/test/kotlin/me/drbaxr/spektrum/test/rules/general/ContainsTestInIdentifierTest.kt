package me.drbaxr.spektrum.test.rules.general

import me.drbaxr.spektrum.flexible.identifiers.rules.general.ContainsTestInIdentifier
import me.drbaxr.spektrum.fixed.model.HierarchyUnit
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ContainsTestInIdentifierTest {
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