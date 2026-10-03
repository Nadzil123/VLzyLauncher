package com.nadzil123.vlzylauncher.game.version.download

import com.nadzil123.vlzylauncher.coroutine.Task
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import mockwebserver3.Dispatcher
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import mockwebserver3.RecordedRequest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

class TaskBatchTest {
    @get:Rule
    val temporaryFolder = TemporaryFolder()

    @Test
    fun `failed verification never reports a completed download`() = runBlocking {
        withTimeout(15_000) {
            MockWebServer().use { server ->
                server.dispatcher = object : Dispatcher() {
                    override fun dispatch(request: RecordedRequest) =
                        MockResponse.Builder().body("bad").build()
                }
                server.start()
                val target = temporaryFolder.root.resolve("client.jar")
                val task = Task.runTask(task = {})
                val progress = mutableListOf<Float>()

                val failure = runCatching {
                    task.runBatchDownloads(
                        tasks = listOf(DownloadTask(
                            urls = listOf(server.url("/client.jar").toString()),
                            verifyIntegrity = true,
                            targetFile = target,
                            sha1 = ABC_SHA1,
                            size = 3
                        )),
                        maxConnections = 1,
                        retryRounds = 0,
                        onSnapshot = { progress += task.progress.value }
                    )
                }.exceptionOrNull()

                assertTrue(failure is DownloadFailedException)
                assertTrue("The retries must produce observable progress", progress.isNotEmpty())
                assertTrue("Unverified bytes must not report 100%: $progress", progress.all { it < 1f })
                assertFalse(target.exists())
            }
        }
    }

    @Test
    fun `a failed source falls back and completes only after verification`() = runBlocking {
        withTimeout(15_000) {
            MockWebServer().use { server ->
                server.dispatcher = object : Dispatcher() {
                    override fun dispatch(request: RecordedRequest) = MockResponse.Builder()
                        .body(if (request.target == "/bad") "bad" else "abc")
                        .build()
                }
                server.start()
                val target = temporaryFolder.root.resolve("client.jar")
                val task = Task.runTask(task = {})
                val prematureCompletion = mutableListOf<Float>()

                task.runBatchDownloads(
                    tasks = listOf(DownloadTask(
                        urls = listOf(server.url("/bad").toString(), server.url("/good").toString()),
                        verifyIntegrity = true,
                        targetFile = target,
                        sha1 = ABC_SHA1,
                        size = 3
                    )),
                    maxConnections = 1,
                    retryRounds = 0,
                    onSnapshot = { snapshot ->
                        if (snapshot.downloadedFiles < snapshot.totalFiles && task.progress.value >= 1f) {
                            prematureCompletion += task.progress.value
                        }
                    }
                )

                assertEquals("abc", target.readText())
                assertEquals(1f, task.progress.value, 0f)
                assertTrue("Fallback retries must not claim completion", prematureCompletion.isEmpty())
            }
        }
    }

    private companion object {
        const val ABC_SHA1 = "a9993e364706816aba3e25717850c26c9cd0d89d"
    }
}
