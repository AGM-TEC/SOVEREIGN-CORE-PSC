package com.fardc.sigint.core

import java.net.ServerSocket
import java.net.SocketTimeoutException
import kotlin.concurrent.thread

class NetworkResilience(private val engine: StateMachine, private val logger: BlackBox) {
    private var isResilient = true

    fun monitorSystem() {
        println("[CORE] Lancement du Watchdog de Résilience Réseau (Port 8888)...")

        thread(name = "NetworkWatchdog") {
            while (isResilient) {
                try {
                    val server = ServerSocket(8888)
                    server.soTimeout = 10000 // 10 secondes pour recevoir un heartbeat
                    
                    while (isResilient) {
                        try {
                            val pulse = server.accept()
                            // Vérification de l'origine (seul le localhost ou les unités Mesh peuvent pinguer)
                            println("[💓] PULSE : Signal de vie reçu de ${pulse.inetAddress.hostAddress}")
                            pulse.close()
                        } catch (e: SocketTimeoutException) {
                            handleNetworkIsolation()
                        }
                    }
                    server.close()
                } catch (e: Exception) {
                    logger.record_incident("NETWORK_REBOOT", "Instabilité détectée sur le port de résilience.")
                    Thread.sleep(5000)
                }
            }
        }
    }

    private fun handleNetworkIsolation() {
        println("⚠️ [RESILIENCE] Isolation réseau détectée. Tentative de bascule...")
        logger.record_incident("NET_ISOLATION", "Passage en mode de communication d'urgence.")
        
        // On force le cerveau en mode FURTIF pour éviter la détection pendant l'isolement
        if (engine.current_mode != "FURTIF") {
            engine.set_mode("FURTIF")
        }
    }
}
