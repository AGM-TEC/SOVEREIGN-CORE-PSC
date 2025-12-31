package com.fardc.sigint.core

import io.javalin.Javalin
import io.javalin.http.Context

enum class CombatState {
    STBY,       // Standby : Système au repos
    RECO,       // Reconnaissance : Interception passive (SIGINT)
    OP,         // Opération active : Interception et exfiltration active
    ENGAGED     // Engagement : Alerte maximale, reporting temps réel (FARDC HQ)
}

class CombatModeHandler {
    private var currentState: CombatState = CombatState.STBY

    // Configuration des points de contrôle API
    fun setupRoutes(app: Javalin) {
        app.get("/admin/combat/status") { ctx -> ctx.json(mapOf("status" to currentState)) }
        
        app.post("/admin/combat/set-mode") { ctx ->
            val mode = ctx.queryParam("mode")?.uppercase()
            try {
                currentState = CombatState.valueOf(mode ?: "STBY")
                println("🕹️ CHANGEMENT D'ÉTAT TACTIQUE : $currentState")
                ctx.result("SYSTÈME PASSÉ EN MODE $currentState")
            } catch (e: Exception) {
                ctx.status(400).result("MODE INVALIDE")
            }
        }
    }

    fun getStatus(): CombatState = currentState
    
    // Logique de décision pour les autres modules
    fun isOffensiveEnabled(): Boolean = currentState == CombatState.OP || currentState == CombatState.ENGAGED
    fun isPassiveInterceptionEnabled(): Boolean = currentState != CombatState.STBY
}
