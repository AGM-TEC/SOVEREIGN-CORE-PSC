package com.fardc.sigint.core

import io.javalin.Javalin
import java.time.Instant

data class TacticalUnit(val id: String, val lat: Double, val lon: Double, val lastUpdate: Instant)

class BFTModule(private val combatHandler: CombatModeHandler) {
    private val activeUnits = mutableMapOf<String, TacticalUnit>()

    fun setup(app: Javalin) {
        // Endpoint pour recevoir les positions des unités sur le terrain
        app.post("/bft/update") { ctx ->
            if (!combatHandler.isBFTOperational()) {
                ctx.status(403).result("🚫 BFT INACTIF : Mode Standby détecté.")
                return@post
            }

            val id = ctx.queryParam("unit_id") ?: "UNKNOWN"
            val lat = ctx.queryParam("lat")?.toDouble() ?: 0.0
            val lon = ctx.queryParam("lon")?.toDouble() ?: 0.0
            
            activeUnits[id] = TacticalUnit(id, lat, lon, Instant.now())
            println("📍 [BFT] Mise à jour unité $id : $lat, $lon")
            ctx.result("Position enregistrée")
        }

        // Vue tactique pour l'État-Major
        app.get("/admin/bft/map") { ctx ->
            if (combatHandler.getStatus() == CombatState.STBY) {
                ctx.result("Carte tactique verrouillée en mode Standby.")
                return@get
            }
            ctx.json(activeUnits.values)
        }
    }
}
