// SPDX-License-Identifier: GPL-3.0-or-later
package com.nadzil123.vlzylauncher.vlzy.storage

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File

class MetadataMigrationTest {
    @get:Rule val temporaryFolder = TemporaryFolder()

    @Test
    fun `failed move returns readable original without creating partial destination`() {
        val original = temporaryFolder.newFile("original.cfg").apply { writeText("saved settings") }
        val blockedParent = temporaryFolder.newFile("not-a-directory")
        val destination = File(blockedParent, "new.cfg")
        assertEquals(original, MetadataMigration.moveIfAbsent(original, destination))
        assertEquals("saved settings", original.readText())
        assertFalse(destination.exists())
    }

    @Test
    fun `destination with the wrong type cannot hide existing metadata`() {
        val original = temporaryFolder.newFolder("old-metadata")
        File(original, "version.config").writeText("saved settings")
        val destination = temporaryFolder.newFile("new-metadata")
        assertEquals(original, MetadataMigration.moveIfAbsent(original, destination))
        assertEquals("saved settings", File(original, "version.config").readText())
    }

    @Test
    fun `file migration preserves binary contents and is idempotent`() {
        val original = temporaryFolder.newFile("old-state")
        val bytes = byteArrayOf(0, 3, 127, -1, 13, 10)
        original.writeBytes(bytes)
        val destination = File(original.parentFile, "new-state")
        assertEquals(destination, MetadataMigration.moveIfAbsent(original, destination))
        org.junit.Assert.assertArrayEquals(bytes, destination.readBytes())
        assertFalse(original.exists())
        assertEquals(destination, MetadataMigration.moveIfAbsent(original, destination))
    }
}
