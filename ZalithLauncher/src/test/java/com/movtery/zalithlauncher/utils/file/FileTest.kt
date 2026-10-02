/*
 * Zalith Launcher 2
 * Copyright (C) 2025 MovTery <movtery228@qq.com> and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/gpl-3.0.txt>.
 */

package com.movtery.zalithlauncher.utils.file

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.jackhuang.hmcl.util.DigestUtils
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

class FileTest {
    @get:Rule
    val temporaryFolder = TemporaryFolder()

    @Test
    fun testCalculateFileSha1() {
        val file = temporaryFolder.newFile("content.bin").apply { writeText("abc") }
        val expected = "a9993e364706816aba3e25717850c26c9cd0d89d"
        runBlocking(Dispatchers.IO) {
            assertEquals(expected, calculateFileSha1(file))
            assertEquals(expected, DigestUtils.digestToString("SHA-1", file.toPath()))
        }
    }
}
