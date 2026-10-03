// SPDX-License-Identifier: GPL-3.0-or-later
package com.nadzil123.vlzylauncher.vlzy.storage

import com.nadzil123.vlzylauncher.vlzy.compatibility.LegacyNames
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File

class GameStateStorageTest {
    @get:Rule val temporaryFolder = TemporaryFolder()

    @Test
    fun `rename preserves selected version and favorites without reserializing`() {
        val home = temporaryFolder.newFolder("game")
        val old = File(home, LegacyNames.GAME_STATE_FILE)
        val json = """{"version":"1.21-forge","favoritesInfo":{"Survival":["1.21-forge"]}}"""
        old.writeText(json)
        val current = GameStateStorage.fileIn(home)
        assertEquals(File(home, "vlzy-game.cfg"), current)
        assertEquals(json, current.readText())
        assertFalse(old.exists())
        assertEquals(current, GameStateStorage.fileIn(home))
    }

    @Test
    fun `current selection is never replaced by older state`() {
        val home = temporaryFolder.newFolder("both-states")
        val old = File(home, LegacyNames.GAME_STATE_FILE).apply { writeText("old") }
        val current = File(home, "vlzy-game.cfg").apply { writeText("current") }
        assertEquals(current, GameStateStorage.fileIn(home))
        assertEquals("current", current.readText())
        assertEquals("old", old.readText())
    }
}
