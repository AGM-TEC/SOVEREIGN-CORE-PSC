package com.fardc.sigint.core

import java.net.ServerSocket
import kotlin.concurrent.thread

/**
 * MODULE PACKET-SNIFFER OPÉRATIONNEL
 * Interception et analyse des flux réseau locaux
 */
class PacketSniffer {
    fun startSniffing(port: Int) {
        println("📡 [SNIFFER] Mise en écoute sur le port $port...")
        thread {
            try {
                val listener = ServerSocket(port)
                while (true) {
                    val packet = listener.accept()
                    val data = packet.getInputStream().bufferedReader().readLine()
                    println("[SNIFFER] Données capturées : $data")
                    packet.close()
                }
            } catch (e: Exception) {
                println("[ERROR] Échec de l'interception : ${e.message}")
            }
        }
    }
}
