// SPDX-License-Identifier: GPL-3.0-or-later
package com.nadzil123.vlzylauncher.vlzy.compatibility

/** A stable rule code plus the explanation shown for a compatibility decision. */
data class CompatibilityReason(
    val code: String,
    val message: String,
    val severity: CompatibilitySeverity
) {
    init {
        require(code.isNotBlank()) { "A compatibility reason needs a rule code." }
        require(message.isNotBlank()) { "A compatibility reason needs an explanation." }
    }
}
