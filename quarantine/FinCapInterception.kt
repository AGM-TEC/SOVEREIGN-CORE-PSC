package com.fardc.sigint.core

import java.net.ServerSocket
import java.net.SocketTimeoutException
import kotlin.concurrent.thread

class FinCapInterception(private val engine: StateMachine, private val logger: BlackBox) {
    private var isActive = true

    fun runInterceptionMode() {
        println("[CYBER-OP] Déploiement du module d'interception sur le port 8889...")

        thread {
            while (isActive) {
                try {
                    val server = ServerSocket(8889)
                    server.soTimeout = 5000 // Évite le blocage infini
                    
                    println("[SIGINT] Écoute active des flux financiers et cyber.")

                    while (isActive) {
                        try {
                            val target = server.accept()
                            val ip = target.inetAddress.hostAddress
                            
                            // LIAISON : Analyse immédiate de l'IP interceptée
                            logger.record_incident("INTERCEPTION_FLUX", "Origine : $ip")
                            
                            // Si le mode est OFFENSIF, on maintient la connexion pour analyse
                            if (engine.current_mode == "OFFENSIF") {
                                handleTarget(target)
                            } else {
                                target.close()
                            }
                        } catch (e: SocketTimeoutException) {
                            continue // Boucle pour vérifier si isActive est toujours vrai
                        }
                    }
                    server.close()
                } catch (e: Exception) {
                    logger.record_incident("CRITICAL_ERR", "Redémarrage module interception : ${e.message}")
                    Thread.sleep(2000) // Pause avant redémarrage automatique
                }
            }
        }
    }

    private fun handleTarget(socket: java.net.Socket) {
        // Logique de capture de données (DPI - Deep Packet Inspection)
        println("[DPI] Extraction des métadonnées du flux...")
        socket.close()
    }
}
