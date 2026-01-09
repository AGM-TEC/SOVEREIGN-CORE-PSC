package com.fardc.sigint.core.sync

import com.fardc.sigint.core.BlackBox
import com.fardc.sigint.security.SecurityVault
import java.util.concurrent.ConcurrentHashMap

/**
 * MESH-SYNC-ENGINE v8.1 (Purified)
 * Standard: Distributed Intel Consistency
 * Rôle: Synchronisation des bases de données de capture entre unités
 */
class MeshSyncEngine(private val logger: BlackBox, private val vault: SecurityVault) {
    
    // Base de données locale en mémoire (Thread-safe)
    private val intelInventory = ConcurrentHashMap<String, String>()

    fun processIncomingSync(partnerId: String, encryptedPayload: String) {
        try {
            // Déchiffrement via le module de sécurité purifié
            val decryptedData = vault.encrypt(encryptedPayload) // Note: Utilisation du vault durci
            val intelId = decryptedData.hashCode().toString()

            if (!intelInventory.containsKey(intelId)) {
                intelInventory[intelId] = decryptedData
                logger.recordIncident("MESH_SYNC", "Nouvelle donnée reçue de $partnerId : $intelId")
            }
        } catch (e: Exception) {
            logger.recordIncident("SYNC_ERR", "Échec de synchronisation avec $partnerId")
        }
    }

    fun generateSyncPayload(): String {
        // Prépare un condensé des dernières captures pour diffusion
        val summary = intelInventory.keys.joinToString(",")
        return vault.encrypt(summary)
    }

    fun getStatus(): String = if (intelInventory.isEmpty()) "IDLE" else "SYNCHRONIZED (${intelInventory.size} items)"
}
