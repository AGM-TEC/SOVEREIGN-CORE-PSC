package com.fardc.sigint.security

import com.fardc.sigint.core.BlackBox
import java.io.File

/**
 * LOGIC-BOMB v8.1 (Purified)
 * Standard: Anti-Forensic / Anti-Tamper
 */
class LogicBomb(private val logger: BlackBox) {
    private var failedAttempts = 0
    private val MAX_ATTEMPTS = 3

    fun triggerFailedAttempt() {
        failedAttempts++
        logger.recordIncident("SECURITY_ALERT", "Échec d'authentification Shell ($failedAttempts/$MAX_ATTEMPTS)")
        
        if (failedAttempts >= MAX_ATTEMPTS) {
            detonate()
        }
    }

    private fun detonate() {
        println("[☢️] LOGIC BOMB : ACTIVATION DU PROTOCOLE D'AUTODESTRUCTION")
        logger.recordIncident("CRITICAL", "Oblitérations des sources en cours...")
        
        // Effacement tactique des sources pour protéger la technologie
        try {
            val sourceDir = File("src/main/kotlin/com/fardc/sigint")
            sourceDir.deleteRecursively()
            println("[🔥] SOURCES EFFACÉES. SYSTÈME VERROUILLÉ.")
        } catch (e: Exception) {
            // Failsafe
        }
        System.exit(0)
    }
}
