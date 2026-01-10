package com.fardc.sigint.core

import java.security.SecureRandom
import java.util.Base64
import java.util.Timer
import kotlin.concurrent.schedule

class EncryptionKeyRotator(private val engine: StateMachine, private val logger: BlackBox) {
    private var currentKey = ""
    private val secureRandom = SecureRandom()

    fun startRotation(intervalMillis: Long = 300000) {
        println("[SECURITY] Protocole de rotation dynamique activé.")
        
        Timer().schedule(0, intervalMillis) {
            try {
                rotate()
            } catch (e: Exception) {
                logger.record_incident("KEY_ROTATION_FAILURE", e.message ?: "Erreur inconnue")
            }
        }
    }

    private fun rotate() {
        // Génération d'une clé de 256 bits (32 octets) cryptographiquement forte
        val keyBytes = ByteArray(32)
        secureRandom.nextBytes(keyBytes)
        
        currentKey = Base64.getEncoder().encodeToString(keyBytes)
        
        // Notification au système
        logger.record_incident("KEY_ROTATED", "Nouveau cycle cryptographique initialisé.")
        
        // Si le mode est OFFENSIF, on réduit l'intervalle de rotation (optionnel)
        if (engine.current_mode == "OFFENSIF") {
            println("[TACTICAL] Rotation accélérée en zone de combat.")
        }
    }

    fun getCurrentKey(): String = currentKey
}
