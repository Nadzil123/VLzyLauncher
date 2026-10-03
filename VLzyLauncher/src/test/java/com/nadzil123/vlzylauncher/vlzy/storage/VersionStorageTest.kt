// SPDX-License-Identifier: GPL-3.0-or-later
package com.nadzil123.vlzylauncher.vlzy.storage

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File

class VersionStorageTest {
    @get:Rule
    val temporaryFolder = TemporaryFolder()

    @Test
    fun `existing version metadata moves to the VLzy directory without changing contents`() {
        val versionDirectory = temporaryFolder.newFolder("versions", "existing-forge-version")
        val legacyDirectory = File(versionDirectory, "ZalithLauncher").apply { mkdirs() }
        val config = File(legacyDirectory, "version.config")
        val original = """{"isolationType":"DISABLE","customPath":"/games/existing","renderer":"existing-renderer","jvmArgs":"-Xmx2G"}"""
        config.writeText(original)
        File(legacyDirectory, "VersionIcon.png").writeBytes(byteArrayOf(1, 2, 3))

        val resolved = VersionStorage.directoryIn(versionDirectory)

        assertEquals(original, File(resolved, "version.config").readText())
        assertEquals(3L, File(resolved, "VersionIcon.png").length())
        assertEquals(File(versionDirectory, "VLzyLauncher"), resolved)
        assertFalse(legacyDirectory.exists())
        assertEquals(resolved, VersionStorage.directoryIn(versionDirectory))
    }

    @Test
    fun `existing destination takes precedence and leaves old settings recoverable`() {
        val versionDirectory = temporaryFolder.newFolder("conflicting-version")
        val legacy = File(versionDirectory, "ZalithLauncher").apply { mkdirs() }
        val current = File(versionDirectory, "VLzyLauncher").apply { mkdirs() }
        File(legacy, "version.config").writeText("old settings")
        File(current, "version.config").writeText("current settings")

        assertEquals(current, VersionStorage.directoryIn(versionDirectory))
        assertEquals("current settings", File(current, "version.config").readText())
        assertEquals("old settings", File(legacy, "version.config").readText())
    }

    @Test
    fun `new versions use VLzy metadata`() {
        val versionDirectory = temporaryFolder.newFolder("new-version")
        assertEquals(File(versionDirectory, "VLzyLauncher"), VersionStorage.directoryIn(versionDirectory))
    }
}
