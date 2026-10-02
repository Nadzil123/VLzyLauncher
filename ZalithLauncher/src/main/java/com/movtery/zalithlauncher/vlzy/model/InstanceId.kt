// SPDX-License-Identifier: GPL-3.0-or-later
package com.movtery.zalithlauncher.vlzy.model

import java.util.UUID

/** Stable instance identity, independent of its display name or game directory. */
@JvmInline
value class InstanceId private constructor(private val value: UUID) {
    override fun toString(): String = value.toString()

    companion object {
        private val canonicalUuid = Regex(
            "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}"
        )

        fun random(): InstanceId = InstanceId(UUID.randomUUID())

        /** Reject shortened UUIDs that UUID.fromString would otherwise pad silently. */
        fun parse(value: String): InstanceId {
            require(canonicalUuid.matches(value)) { "InstanceId must be a canonical UUID." }
            return InstanceId(UUID.fromString(value))
        }
    }
}
