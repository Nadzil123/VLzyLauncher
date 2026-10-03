// SPDX-License-Identifier: GPL-3.0-or-later
package com.nadzil123.vlzylauncher.vlzy.storage

import java.io.File
import java.io.IOException
import java.nio.file.Files

/** Move metadata on first access, keeping the existing data usable if a move fails. */
internal object MetadataMigration {
    @Synchronized
    fun moveIfAbsent(source: File, destination: File): File {
        if (!source.exists()) return destination
        if (destination.exists()) {
            // A conflicting file/directory type must not hide usable legacy data.
            return if (source.isDirectory == destination.isDirectory) destination else source
        }
        try {
            // No REPLACE_EXISTING: an existing destination always belongs to the user.
            Files.move(source.toPath(), destination.toPath())
            return destination
        } catch (_: IOException) {
            return if (!source.exists() && destination.exists()) destination else source
        } catch (_: SecurityException) {
            return source
        }
    }
}
