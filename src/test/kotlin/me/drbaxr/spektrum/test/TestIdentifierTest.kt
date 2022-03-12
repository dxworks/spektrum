package me.drbaxr.spektrum.test

import me.drbaxr.spektrum.identifiers.TestIdentifier
import me.drbaxr.spektrum.identifiers.rules.ContainsTestInIdentifier
import me.drbaxr.spektrum.identifiers.rules.Rule
import me.drbaxr.spektrum.main.model.HierarchyUnit
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class TestIdentifierTest {
    @Test
    fun testIdentifierWithSingleRule() {
        val identifier = TestIdentifier(listOf(
            ContainsTestInIdentifier()
        ))

        val unit = HierarchyUnit(
            "someTestUnit",
            mutableSetOf(),
            "METHOD"
        )

        assertTrue { identifier.isTest(unit) }
    }

    @Test
    fun testIdentifierWithMultipleRulesFalse() {
        val identifier = TestIdentifier(listOf(
            ContainsTestInIdentifier(),
            object : Rule {
                override fun isRespectedBy(unit: HierarchyUnit): Boolean {
                    return false
                }
            },
            object : Rule {
                override fun isRespectedBy(unit: HierarchyUnit): Boolean {
                    return true
                }
            }
        ))

        val unit = HierarchyUnit(
            "someTestUnit",
            mutableSetOf(),
            "METHOD"
        )

        assertFalse { identifier.isTest(unit) }
    }

    @Test
    fun testIdentifierWithMultipleRulesTrue() {
        val identifier = TestIdentifier(listOf(
            ContainsTestInIdentifier(),
            object : Rule {
                override fun isRespectedBy(unit: HierarchyUnit): Boolean {
                    return true
                }
            },
            object : Rule {
                override fun isRespectedBy(unit: HierarchyUnit): Boolean {
                    return true
                }
            }
        ))

        val unit = HierarchyUnit(
            "someTestUnit",
            mutableSetOf(),
            "METHOD"
        )

        assertTrue { identifier.isTest(unit) }
    }
}