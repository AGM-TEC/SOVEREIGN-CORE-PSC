package com.fardc.sigint.core

import java.net.ServerSocket
import kotlin.concurrent.thread

/**
 * Module de Résilience Réseau
 * Gère la détection d'activité sur les ports sécurisés
 */
class NetworkResilience {
    fun monitorSystem() {
        val port = 8888
        println("[CORE] Activation du bouclier sur le port $port...")
        
        thread {
            try {
                // Ouverture réelle du socket pour monitoring
                val server = ServerSocket(port)
                println("[SIGINT] Bouclier actif. En attente de signaux...")
                
                while (true) {
                    val client = server.accept()
                    // Capture l'adresse IP de l'activité détectée
                    println("[ALERTE] Activité détectée provenant de : ${client.inetAddress.hostAddress}")
                    client.close()
                }
            } catch (e: Exception) {
                // Gestion des erreurs système (ex: port déjà utilisé)
                println("[ERREUR] Échec du bouclier réseau : ${e.message}")
            }
        }
    }
}
