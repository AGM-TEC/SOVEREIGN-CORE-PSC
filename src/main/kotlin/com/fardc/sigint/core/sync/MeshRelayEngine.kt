package com.fardc.sigint.core.sync

import com.fardc.sigint.core.BlackBox
import com.fardc.sigint.utils.CryptoUtils

/**
 * MESH-RELAY-ENGINE v8.1 (Purified)
 * Standard: Tactical Decentralized Sync
 * Rôle: Propagation de renseignement en zone déconnectée
 */
class MeshRelayEngine(private val logger: BlackBox) {
    
    data class RelayPacket(
        val originId: String, 
        val payload: String, 
        val hopCount: Int,
        val signature: String
    )

    private val MAX_HOPS = 5 // Limite de sécurité militaire pour éviter la détection

    fun forwardPacket(packet: RelayPacket): RelayPacket? {
        if (packet.hopCount >= MAX_HOPS) {
            logger.recordIncident("MESH_DROP", "Paquet expiré : trop de sauts (${packet.hopCount})")
            return null
        }

        // Vérification d'intégrité avant relais
        val expectedSig = CryptoUtils.generateChecksum(packet.originId + packet.payload)
        if (packet.signature != expectedSig) {
            logger.recordIncident("MESH_TAMPER", "Alerte : Paquet Mesh corrompu détecté !")
            return null
        }

        val newPacket = packet.copy(hopCount = packet.hopCount + 1)
        println("[📡] MESH : Relais validé (Saut: ${newPacket.hopCount}) | ID: ${packet.originId}")
        
        return newPacket
    }
}
