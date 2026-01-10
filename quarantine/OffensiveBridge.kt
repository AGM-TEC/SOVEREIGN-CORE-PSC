package com.fardc.sigint.core

import java.io.BufferedReader
import java.io.InputStreamReader

class OffensiveBridge(private val logger: BlackBox) {

    fun executeScan(target: String): String {
        // 1. ASSAINISSEMENT (Sanitization)
        // On ne garde que les caractères alphanumériques et les points (IP/Domaine)
        val sanitizedTarget = target.replace(Regex("[^a-zA-Z0-9.-]"), "")
        
        println("[OFFENSIVE] Lancement du diagnostic système sur : $sanitizedTarget")
        logger.record_incident("NETWORK_SCAN_START", "Cible: $sanitizedTarget")

        return try {
            // 2. EXÉCUTION CONTRÔLÉE
            val process = ProcessBuilder("ping", "-c", "2", "-W", "2", sanitizedTarget)
                .redirectErrorStream(true)
                .start()

            val output = process.inputStream.bufferedReader().use { it.readText() }
            val exitCode = process.waitFor()

            if (exitCode == 0) {
                logger.record_incident("SCAN_SUCCESS", "Réponse reçue de $sanitizedTarget")
                "[SUCCESS] Cible active :\n$output"
            } else {
                "[FAILURE] Aucune réponse ou accès refusé par la cible."
            }
        } catch (e: Exception) {
            logger.record_incident("SCAN_CRITICAL_FAIL", e.message ?: "Unknown error")
            "[ERROR] Échec de l'infrastructure d'exécution."
        }
    }
}
