package com.nadzil123.vlzylauncher.vlzy.compatibility

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Test

class CompatibilityResultTest {
    private fun reason(severity: CompatibilitySeverity) =
        CompatibilityReason("JAVA_REQUIREMENT", "Selected Java is below the requirement.", severity)

    @Test
    fun completedEvaluationWithNoFindingsIsCompatible() {
        assertTrue(CompatibilityResult(emptyList()).isCompatible)
    }

    @Test
    fun informationalAndWarningFindingsDoNotBlockCompatibility() {
        val result = CompatibilityResult(listOf(
            reason(CompatibilitySeverity.INFO), reason(CompatibilitySeverity.WARNING)
        ))
        assertTrue(result.isCompatible)
        assertEquals(2, result.reasons.size)
    }

    @Test
    fun anErrorBlocksCompatibilityAndPreservesTheExplanation() {
        val error = reason(CompatibilitySeverity.ERROR)
        val result = CompatibilityResult(listOf(reason(CompatibilitySeverity.WARNING), error))
        assertFalse(result.isCompatible)
        assertEquals(error, result.reasons.last())
    }

    @Test
    fun callerCannotChangeCompletedEvaluationByChangingTheInputList() {
        val reasons = mutableListOf(reason(CompatibilitySeverity.ERROR))
        val result = CompatibilityResult(reasons)
        reasons.clear()
        assertFalse(result.isCompatible)
        assertEquals(1, result.reasons.size)
    }

    @Test
    fun reasonsRequireAStableCodeAndHumanReadableExplanation() {
        assertThrows(IllegalArgumentException::class.java) {
            CompatibilityReason(" ", "Explanation", CompatibilitySeverity.ERROR)
        }
        assertThrows(IllegalArgumentException::class.java) {
            CompatibilityReason("CODE", "\n", CompatibilitySeverity.WARNING)
        }
    }
}
