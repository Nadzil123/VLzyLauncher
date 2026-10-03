package com.nadzil123.vlzylauncher.path

import com.nadzil123.vlzylauncher.BuildKeys
import com.nadzil123.vlzylauncher.game.account.microsoft.AuthType
import com.nadzil123.vlzylauncher.game.account.microsoft.fetchDeviceCodeResponse
import com.nadzil123.vlzylauncher.game.account.microsoft.microsoftAuthAsync
import io.ktor.client.request.HttpRequestPipeline
import io.ktor.client.request.get
import kotlinx.coroutines.runBlocking
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assume.assumeTrue
import org.junit.Test
import java.io.IOException
import java.util.concurrent.atomic.AtomicBoolean

class MissingServiceConfigurationTest {
    @Test
    fun `OkHttp rejects an unconfigured CurseForge API before sending a request`() {
        assumeTrue(BuildKeys.CURSEFORGE_API.isBlank())
        var sentRequests = 0
        val client = createOkHttpClientBuilder { builder ->
            builder.addInterceptor { chain ->
                sentRequests++
                Response.Builder().request(chain.request()).protocol(Protocol.HTTP_1_1)
                    .code(403).message("Forbidden").body("".toResponseBody()).build()
            }
        }.build()

        val error = runCatching {
            client.newCall(Request.Builder().url("https://api.curseforge.com/v1/mods/search").build())
                .execute().use { }
        }.exceptionOrNull()

        assertNotConfigured(error, "CurseForge")
        assertEquals(0, sentRequests)
    }

    @Test
    fun `Ktor rejects an unconfigured CurseForge API before sending a request`() = runBlocking {
        assumeTrue(BuildKeys.CURSEFORGE_API.isBlank())
        val active = AtomicBoolean(true)
        try {
            GLOBAL_CLIENT.requestPipeline.intercept(HttpRequestPipeline.Send) {
                if (active.get() && context.url.host == HOST_CURSEFORGE_API) {
                    throw AssertionError("Unconfigured CurseForge request reached the send phase")
                }
            }
            val error = runCatching {
                GLOBAL_CLIENT.get("https://api.curseforge.com/v1/mods/search")
            }.exceptionOrNull()

            assertNotConfigured(error, "CurseForge")
        } finally {
            active.set(false)
        }
    }

    @Test
    fun `public downloads remain available without a CurseForge key`() {
        assumeTrue(BuildKeys.CURSEFORGE_API.isBlank())
        val visitedHosts = mutableListOf<String>()
        val client = createOkHttpClientBuilder { builder ->
            builder.addInterceptor { chain ->
                val request = chain.request()
                visitedHosts += request.url.host
                assertEquals(null, request.header("x-api-key"))
                Response.Builder().request(request).protocol(Protocol.HTTP_1_1)
                    .code(200).message("OK").body("content".toResponseBody()).build()
            }
        }.build()

        for (host in listOf("mediafilez.forgecdn.net", "api.modrinth.com", "api.curseforge.com.example.org")) {
            client.newCall(Request.Builder().url("https://$host/file").build()).execute().use {
                assertEquals("content", it.body.string())
            }
        }
        assertEquals(3, visitedHosts.size)
    }

    @Test
    fun `Microsoft login rejects missing client ID before requesting a device code`() = runBlocking {
        withoutMicrosoftNetworkRequests {
            val error = runCatching { fetchDeviceCodeResponse(coroutineContext) }.exceptionOrNull()
            assertNotConfigured(error, "Microsoft")
        }
    }

    @Test
    fun `Microsoft refresh rejects missing client ID before sending a token`() = runBlocking {
        withoutMicrosoftNetworkRequests {
            val error = runCatching {
                microsoftAuthAsync(AuthType.Refresh, "test-refresh-token", context = coroutineContext) { }
            }.exceptionOrNull()
            assertNotConfigured(error, "Microsoft")
        }
    }

    private suspend fun withoutMicrosoftNetworkRequests(action: suspend () -> Unit) {
        assumeTrue(BuildKeys.OAUTH_CLIENT_ID.isBlank())
        val active = AtomicBoolean(true)
        GLOBAL_CLIENT.requestPipeline.intercept(HttpRequestPipeline.Before) {
            if (active.get() && context.url.host in listOf("login.microsoftonline.com", "login.live.com")) {
                throw AssertionError("Unconfigured Microsoft login reached the HTTP client")
            }
        }
        try {
            action()
        } finally {
            active.set(false)
        }
    }

    private fun assertNotConfigured(error: Throwable?, service: String) {
        assertTrue("Expected a configuration error, got $error", error is IOException)
        assertTrue("Expected a service-specific setup message, got $error",
            error!!.message.orEmpty().contains(service) && error.message.orEmpty().contains("not configured"))
    }
}
