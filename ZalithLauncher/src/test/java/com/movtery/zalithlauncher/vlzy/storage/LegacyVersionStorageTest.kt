// SPDX-License-Identifier: GPL-3.0-or-later
package com.movtery.zalithlauncher.vlzy.storage

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File

class LegacyVersionStorageTest {
    @get:Rule
    val temporaryFolder = TemporaryFolder()

    @Test
    fun `existing shared version metadata remains readable and writable without relocation`() {
        val versionDirectory = temporaryFolder.newFolder("versions", "existing-forge-version")
        val legacyDirectory = File(versionDirectory, "ZalithLauncher").apply { mkdirs() }
        val config = File(legacyDirectory, "version.config")
        val original = """{"isolationType":"DISABLE","customPath":"/games/existing","renderer":"existing-renderer","jvmArgs":"-Xmx2G"}"""
        config.writeText(original)
        File(legacyDirectory, "VersionIcon.png").writeBytes(byteArrayOf(1, 2, 3))

        val resolved = LegacyVersionStorage.directoryIn(versionDirectory)

        assertEquals(original, File(resolved, "version.config").readText())
        assertEquals(3L, File(resolved, "VersionIcon.png").length())
        File(resolved, "version.config").appendText("\n")
        assertEquals(original + "\n", config.readText())
        assertEquals(legacyDirectory.name, LegacyVersionStorage.DIRECTORY_NAME)
        assertFalse(File(versionDirectory, "VLzyLauncher").exists())
    }
}
