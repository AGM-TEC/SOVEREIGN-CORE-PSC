package com.fardc.sigint.core.sync

import com.fardc.sigint.core.CombatModeHandler
import com.fardc.sigint.core.CombatState
import com.fardc.sigint.core.SecurityVault
import io.javalin.Javalin
import java.time.Instant

/**
 * Moteur de synchronisation maillée (Mesh).
 * Permet la réplication des données entre unités mobiles FARDC en zone grise.
 */
class MeshSyncEngine(
    private val vault: SecurityVault,
    private val combatHandler: CombatModeHandler
) {
    // Liste des nœuds alliés détectés à proximité
    private val peerNodes = mutableSetOf<String>()

    /**
     * Configure les points d'entrée pour la synchronisation P2P.
     */
    fun setup(app: Javalin) {
        
        // Endpoint pour recevoir des données d'une autre unité SIGINT
        app.post("/mesh/sync/push") { ctx ->
            if (combatHandler.getStatus() == CombatState.STBY) {
                ctx.status(403).result("MESH_LOCKED: Système en veille.")
                return@post
            }

            val remotePayload = ctx.body()
            println("🔗 [MESH] Paquet de synchronisation reçu d'un allié.")
            
            // Stockage sécurisé de la donnée synchronisée
            vault.encryptData("MESH_SYNC_RCV | FROM: ${ctx.ip()} | DATA: $remotePayload")
            ctx.result("ACK_SYNC_SUCCESS")
        }

        // Endpoint pour déclarer sa présence (Beacons)
        app.get("/mesh/beacon") { ctx ->
            if (combatHandler.isBFTOperational()) {
                ctx.json(mapOf(
                    "unit_id" to "SIGINT-UNIT-ALPHA",
                    "mode" to combatHandler.getStatus(),
                    "timestamp" to Instant.now().toString()
                ))
            } else {
                ctx.status(404)
            }
        }
    }

    /**
     * Diffuse une alerte critique à tous les nœuds du maillage.
     * Utilisé par l'AnomalyDetector et le FallbackTransmitter.
     */
    fun broadcastThreat(threatMessage: String) {
        val state = combatHandler.getStatus()
        
        if (state == CombatState.OP || state == CombatState.ENGAGED) {
            println("📢 [MESH-BROADCAST] Diffusion d'alerte critique : $threatMessage")
            
            // Simulation d'envoi multicast vers les IP des pairs détectés
            peerNodes.forEach { ip ->
                println("📡 Envoi vers le nœud allié : $ip")
            }
            
            // Log local de la diffusion
            vault.encryptData("MESH_BCAST | MSG: $threatMessage | TS: ${Instant.now()}")
        }
    }

    fun addPeer(ip: String) {
        peerNodes.add(ip)
        println("🤝 [MESH] Nouveau nœud allié enregistré : $ip")
    }
}
