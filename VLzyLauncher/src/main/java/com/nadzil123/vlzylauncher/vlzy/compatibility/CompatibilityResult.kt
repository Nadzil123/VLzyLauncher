// SPDX-License-Identifier: GPL-3.0-or-later
package com.nadzil123.vlzylauncher.vlzy.compatibility

import java.util.Collections

/**
 * Immutable evidence from a completed compatibility evaluation.
 * An empty result means the evaluated rules found no problems; it does not
 * establish compatibility for components whose requirements have not been evaluated.
 */
class CompatibilityResult(reasons: List<CompatibilityReason>) {
    val reasons: List<CompatibilityReason> = Collections.unmodifiableList(reasons.toList())

    val isCompatible: Boolean = this.reasons.none { it.severity == CompatibilitySeverity.ERROR }
}
