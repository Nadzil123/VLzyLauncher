package com.movtery.zalithlauncher.game.download.jvm_server

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.SocketException

class JVMSocketServerTest {
    private val loopback = InetAddress.getByName("127.0.0.1")

    @After
    fun stopServer() {
        JVMSocketServer.stop()
    }

    @Test
    fun `occupied port fails at startup instead of leaving installer waiting`() {
        DatagramSocket(0, loopback).use { occupied ->
            val failure = runCatching {
                JVMSocketServer.start(port = occupied.localPort) {}
            }.exceptionOrNull()

            assertTrue("Socket startup must report a bind failure to its caller", failure is SocketException)
        }
    }

    @Test
    fun `installer exit sent immediately after startup is received`() = runBlocking {
        val port = unusedPort()
        val exit = CompletableDeferred<String>()

        JVMSocketServer.start(port = port) { exit.complete(it) }
        sendExit(port, "0")

        assertEquals("0", withTimeout(5_000) { exit.await() })
        assertEquals("0", JVMSocketServer.receiveMsg)
    }

    @Test
    fun `restart releases previous listener and receives the next installer exit`() = runBlocking {
        val port = unusedPort()
        val firstExit = CompletableDeferred<String>()
        JVMSocketServer.start(port = port) { firstExit.complete(it) }
        sendExit(port, "1")
        assertEquals("1", withTimeout(5_000) { firstExit.await() })

        val secondExit = CompletableDeferred<String>()
        JVMSocketServer.start(port = port) { secondExit.complete(it) }
        sendExit(port, "0")

        assertEquals("0", withTimeout(5_000) { secondExit.await() })
        JVMSocketServer.stop()
        DatagramSocket(port, loopback).use { assertEquals(port, it.localPort) }
    }

    private fun unusedPort(): Int = DatagramSocket(0, loopback).use { it.localPort }

    private fun sendExit(port: Int, code: String) {
        DatagramSocket().use { sender ->
            val bytes = code.toByteArray()
            sender.send(DatagramPacket(bytes, bytes.size, loopback, port))
        }
    }
}
