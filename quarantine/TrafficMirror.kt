package com.fardc.sigint.core

import java.net.ServerSocket
import java.net.Socket
import kotlin.concurrent.thread

/**
 * MODULE TRAFFIC-MIRRORING PSC
 * Duplication discrète des flux réseau
 */
class TrafficMirror {
    fun startMirroring(sourcePort: Int, mirrorPort: Int) {
        println("[MIRROR] Activation du miroir : Port $sourcePort -> Port $mirrorPort")
        thread {
            try {
                val server = ServerSocket(sourcePort)
                while (true) {
                    val client = server.accept()
                    // Duplication du flux vers le port de monitoring
                    thread { forwardTraffic(client, mirrorPort) }
                }
            } catch (e: Exception) {
                println("[ERROR] Échec du miroir : ${e.message}")
            }
        }
    }

    private fun forwardTraffic(client: Socket, mirrorPort: Int) {
        try {
            val monitor = Socket("localhost", mirrorPort)
            client.getInputStream().copyTo(monitor.getOutputStream())
            monitor.close()
            client.close()
        } catch (e: Exception) { /* Silenced for stealth */ }
    }
}
