// SPDX-License-Identifier: GPL-3.0-or-later
package com.nadzil123.vlzylauncher.vlzy.storage

import java.io.File
import com.nadzil123.vlzylauncher.vlzy.compatibility.LegacyNames

/** VLzy version settings, icons and logs, with lossless migration on first access. */
object VersionStorage {
    const val DIRECTORY_NAME = "VLzyLauncher"

    fun directoryIn(versionDirectory: File): File = MetadataMigration.moveIfAbsent(
        File(versionDirectory, LegacyNames.VERSION_DIRECTORY),
        File(versionDirectory, DIRECTORY_NAME)
    )
}
