// SPDX-License-Identifier: GPL-3.0-or-later
package com.nadzil123.vlzylauncher.vlzy.storage

import com.nadzil123.vlzylauncher.vlzy.compatibility.LegacyNames
import java.io.File

/** Preserve selected version and favorites when adopting the VLzy state filename. */
object GameStateStorage {
    fun fileIn(gameHome: File): File = MetadataMigration.moveIfAbsent(
        File(gameHome, LegacyNames.GAME_STATE_FILE),
        File(gameHome, "vlzy-game.cfg")
    )
}
