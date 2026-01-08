package com.fardc.sigint.core

import java.net.Socket
import java.net.InetSocketAddress
import java.security.SecureRandom

class OffensiveScanner(private val logger: BlackBox, private val brain: StateMachine) {
    private val random = SecureRandom()

    fun performTacticalScan(ip: String, ports: List<Int>) {
        // 1. Vérification des ROE (Règles d'engagement)
        if (brain.current_mode == "VEILLE") {
            println("[🚫] SCAN REFUSÉ : Mode OFFENSIF requis pour l'émission active.")
            return
        }

        println("[🚀] V8.0 OFFENSIVE : Scan furtif sur cible $ip...")
        
        // Mélange des ports pour casser la signature du scan
        val shuffledPorts = ports.shuffled()

        for (port in shuffledPorts) {
            try {
                val socket = Socket()
                // Timeout adaptatif : plus long en mode furtif pour paraître naturel
                val timeout = if (brain.current_mode == "FURTIF") 500 else 150
                
                socket.connect(InetSocketAddress(ip, port), timeout)
                logger.record_incident("TARGET_VULN", "Cible: $ip | Port Ouvert: $port")
                println("[🎯] CIBLE VULNÉRABLE : $ip:$port")
                socket.close()

                // Délai aléatoire entre les ports (Éviter la détection IDS)
                Thread.sleep(random.nextInt(50, 200).toLong())

            } catch (e: Exception) {
                // Silence radio sur les ports fermés
            }
        }
    }
}
