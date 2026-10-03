package com.nadzil123.vlzylauncher.vlzy.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertThrows
import org.junit.Test
import java.util.UUID

class InstanceIdTest {
    private val canonical = "52f12ba9-1e0a-4b34-b210-b6048731c580"

    @Test
    fun parsesAndSerializesCanonicalIdentity() {
        assertEquals(canonical, InstanceId.parse(canonical).toString())
    }

    @Test
    fun uppercaseAndLowercaseNamesReferToTheSameIdentity() {
        val id = InstanceId.parse(canonical)
        val uppercase = InstanceId.parse(canonical.uppercase())
        assertEquals(id, uppercase)
        assertEquals(canonical, uppercase.toString())
        assertEquals(1, setOf(id, uppercase).size)
    }

    @Test
    fun rejectsMalformedAndAbbreviatedIdentities() {
        listOf("", "instance-name", "1-1-1-1-1", " $canonical", "$canonical\n",
            canonical.replace("-", ""), canonical.replaceFirst('5', 'g')).forEach { value ->
            assertThrows("Should reject '$value'", IllegalArgumentException::class.java) {
                InstanceId.parse(value)
            }
        }
    }

    @Test
    fun generatedIdentityCanBePersistedAndRestored() {
        val first = InstanceId.random()
        val second = InstanceId.random()
        assertNotEquals(first, second)
        assertEquals(4, UUID.fromString(first.toString()).version())
        assertEquals(first, InstanceId.parse(first.toString()))
    }
}
