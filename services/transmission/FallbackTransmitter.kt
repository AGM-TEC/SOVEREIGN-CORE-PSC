
package com.fardc.sigint.services.transmission

import com.fardc.sigint.core.CombatModeHandler
import com.fardc.sigint.core.CombatState
import com.fardc.sigint.core.SecurityVault
import java.time.Instant

/**
 * Module de transmission de secours (Fallback).
 * Assure la continuité de la mission en environnement contesté.
 */
class FallbackTransmitter(
    private val vault: SecurityVault,
    private val combatHandler: CombatModeHandler
) {

    /**
     * Transmet une alerte ou une preuve SIGINT via les canaux de réserve.
     */
    fun transmitEmergency(payload: String): Boolean {
        val state = combatHandler.getStatus()

        // Le mode Fallback ne s'active que si le système est engagé ou en opération
        if (state == CombatState.STBY || state == CombatState.RECO) {
            println("ℹ️ [FALLBACK] Transmission ignorée : Mode tactique trop bas.")
            return false
        }

        println("🚨 [FALLBACK] Détection de perte de liaison principale. Activation des protocoles de secours...")

        // 1. Chiffrement renforcé pour canal non sécurisé
        val secureData = vault.encryptData("EMERGENCY_TX|${Instant.now()}|$payload")

        // 2. Tentative via MeshSyncEngine (P2P)
        val meshSuccess = tryMeshRelay(secureData)
        
        if (meshSuccess) {
            println("✅ [FALLBACK] Transmission réussie via relais Mesh P2P.")
            return true
        }

        // 3. Bascule ultime : Canal HF / SMS chiffré (Simulation matérielle)
        return tryNarrowBandBroadcast(secureData)
    }

    private fun tryMeshRelay(data: String): Boolean {
        // Logique de recherche de nœuds alliés via Wi-Fi Direct
        println("📡 Tentative de relais via nœuds alliés (MeshSyncEngine)...")
        return true // Simulé
    }

    private fun tryNarrowBandBroadcast(data: String): Boolean {
        // Envoi de paquets fragmentés pour fréquences HF ou SMS
        println("📻 Diffusion sur fréquence de secours HF (High Frequency)...")
        return true // Simulé
    }
}
