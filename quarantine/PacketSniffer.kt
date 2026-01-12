package com.fardc.sigint.core

import java.net.ServerSocket
import java.util.concurrent.Executors
import kotlin.concurrent.thread

class PacketSniffer(private val vault: SecurityVault, private val logger: BlackBox) {
    
    // Pool de threads pour gérer plusieurs interceptions simultanées sans ralentir le noyau
    private val dispatcher = Executors.newFixedThreadPool(10)

    fun startTacticalSniffing(port: Int) {
        println("📡 [V8.0 SNIFFER] Surveillance passive activée sur le port $port.")
        
        thread(isDaemon = true, name = "Sniffer-Core") {
            try {
                val server = ServerSocket(port)
                while (true) {
                    val client = server.accept()
                    
                    // On délègue le traitement à un thread secondaire pour rester réactif
                    dispatcher.execute {
                        try {
                            client.getInputStream().bufferedReader().use { reader ->
                                val payload = reader.readLine()
                                if (payload != null) {
                                    // 1. Analyse de signature (Recherche de mots-clés sensibles)
                                    if (payload.contains("pass") || payload.contains("key") || payload.contains("admin")) {
                                        println("[🎯] DONNÉES SENSIBLES INTERCEPTÉES.")
                                        // 2. Chiffrement immédiat avant stockage
                                        vault.encryptData("SNIFFED_PAYLOAD: $payload")
                                        logger.record_incident("SNIFFER_HITS", "Source: ${client.inetAddress.hostAddress}")
                                    }
                                }
                            }
                        } finally {
                            client.close()
                        }
                    }
                }
            } catch (e: Exception) {
                logger.record_incident("SNIFFER_ERROR", e.message ?: "Unknown")
            }
        }
    }
}
