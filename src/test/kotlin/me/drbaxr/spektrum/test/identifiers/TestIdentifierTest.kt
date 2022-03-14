package me.drbaxr.spektrum.test.identifiers

import me.drbaxr.spektrum.flexible.identifiers.TestIdentifier
import me.drbaxr.spektrum.flexible.identifiers.rules.general.ContainsTestInIdentifier
import me.drbaxr.spektrum.flexible.identifiers.rules.Rule
import me.drbaxr.spektrum.fixed.model.HierarchyUnit
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