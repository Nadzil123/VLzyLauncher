// SPDX-License-Identifier: GPL-3.0-or-later
package com.movtery.zalithlauncher.vlzy.storage

import java.io.File

/** Keep shared version settings, icons and logs at their existing upstream paths. */
object LegacyVersionStorage {
    // This is a storage identity, independent of display branding. Changing it
    // requires an explicit, recoverable migration of existing VersionConfig data.
    const val DIRECTORY_NAME = "ZalithLauncher"

    fun directoryIn(versionDirectory: File): File = File(versionDirectory, DIRECTORY_NAME)
}
