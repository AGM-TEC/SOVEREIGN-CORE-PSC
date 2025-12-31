package com.fardc.sigint.core

import java.net.InetSocketAddress
import java.net.ServerSocket
import java.time.Instant

fun main() {
    println("✅ SOVEREIGN CORE boot OK @ " + Instant.now())
    // Serveur minimal sur port éphémère (0) pour test local
    val server = ServerSocket()
    server.bind(InetSocketAddress("127.0.0.1", 0))
    val port = server.localPort
    println("📡 Minimal socket online on 127.0.0.1:$port")

    // Thread d'acceptation simple
    Thread {
        try {
            val client = server.accept()
            client.getOutputStream().use { out ->
                out.write("COMBAT_READY\n".toByteArray())
                out.flush()
            }
            client.close()
        } catch (e: Exception) {
            System.err.println("Socket error: " + e.message)
        } finally {
            try { server.close() } catch (_: Exception) {}
        }
    }.start()

    // Garde le processus vivant quelques secondes puis termine
    Thread.sleep(3000)
    println("🛑 Shutdown.")
}
