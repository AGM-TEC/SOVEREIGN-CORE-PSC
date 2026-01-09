package com.fardc.sigint.core

import java.net.ServerSocket
import java.net.Socket
import java.io.InputStream
import java.io.OutputStream

/**
 * TRAFFIC-MIRROR v8.1 (Purified)
 * Standard: Passive Network TAP
 * Rôle: Duplication bidirectionnelle des flux pour analyse SIGINT
 */
class TrafficMirror(private val logger: BlackBox) {
    private var isRunning = false

    fun startMirror(listenPort: Int, destinationHost: String, destinationPort: Int, monitorPort: Int) {
        isRunning = true
        logger.recordIncident("MIRROR_INIT", "Démarrage du TAP sur port $listenPort")

        Thread {
            try {
                val server = ServerSocket(listenPort)
                while (isRunning) {
                    val clientSocket = server.accept()
                    val targetSocket = Socket(destinationHost, destinationPort)
                    val monitorSocket = Socket("localhost", monitorPort)

                    // Duplication bidirectionnelle Full-Duplex
                    spawnBridge(clientSocket.getInputStream(), targetSocket.getOutputStream(), monitorSocket.getOutputStream())
                    spawnBridge(targetSocket.getInputStream(), clientSocket.getOutputStream(), monitorSocket.getOutputStream())
                }
            } catch (e: Exception) {
                logger.recordIncident("MIRROR_CRIT", "Rupture du miroir : ${e.message}")
            }
        }.start()
    }

    private fun spawnBridge(input: InputStream, output: OutputStream, mirror: OutputStream) {
        Thread {
            try {
                val buffer = ByteArray(4096)
                var bytesRead: Int
                while (input.read(buffer).also { bytesRead = it } != -1) {
                    // 1. Envoi à la destination réelle (Trafic normal)
                    output.write(buffer, 0, bytesRead)
                    // 2. Copie vers le port de monitoring (SIGINT)
                    mirror.write(buffer, 0, bytesRead)
                }
            } catch (e: Exception) {}
        }.start()
    }
}
