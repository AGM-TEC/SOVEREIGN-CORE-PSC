package com.fardc.sigint.core

import java.net.Socket
import java.net.InetSocketAddress

/**
 * MODULE DE SCAN OFFENSIF PSC
 * Détection réelle des vulnérabilités réseau
 */
class OffensiveScanner {
    fun performPortScan(ip: String, startPort: Int, endPort: Int) {
        println("[OFFENSIVE] Lancement du scan sur $ip (Ports $startPort-$endPort)...")
        
        for (port in startPort..endPort) {
            try {
                val socket = Socket()
                // Timeout court pour un scan rapide
                socket.connect(InetSocketAddress(ip, port), 200)
                println("[DETECTED] Port ouvert : $port")
                socket.close()
            } catch (e: Exception) {
                // Port fermé ou filtré
            }
        }
        println("[OFFENSIVE] Scan terminé.")
    }
}
