package com.fardc.sigint.core

import io.javalin.Javalin
import io.javalin.http.Context

enum class CombatState {
    STBY,       // Repos : Sécurité passive uniquement
    RECO,       // Reconnaissance : Interception passive, BFT actif, Phishing discret
    OP,         // Opérationnelle : Capture financière active, Exfiltration cadencée
    ENGAGED     // Engagement : Assaut total, Reporting temps réel, Alerte HQ
}

class CombatModeHandler {
    private var currentState: CombatState = CombatState.STBY

    fun setupRoutes(app: Javalin) {
        app.get("/admin/combat/status") { ctx -> 
            ctx.json(mapOf(
                "current_mode" to currentState,
                "capabilities" to getActiveCapabilities()
            )) 
        }

        app.post("/admin/combat/set-mode") { ctx ->
            val mode = ctx.queryParam("mode")?.uppercase()
            try {
                currentState = CombatState.valueOf(mode ?: "STBY")
                println("🕹️ ALERTE TACTIQUE : Passage en mode $currentState")
                ctx.result("SYSTÈME RECONFIGURÉ : MODE $currentState ACTIVÉ")
            } catch (e: Exception) {
                ctx.status(400).result("ERREUR : MODE INCONNU")
            }
        }
    }

    // --- LOGIQUE OPÉRATIONNELLE (Basée sur les Manuels) ---

    /**
     * Définit si le module BFT (Blue Force Tracking) doit émettre/recevoir.
     * Actif dès le mode RECO pour la cartographie.
     */
    fun isBFTOperational(): Boolean = currentState != CombatState.STBY

    /**
     * Autorise les capacités offensives financières (M-Pesa, PagoMovil).
     * Strictement réservé aux modes OP et ENGAGED.
     */
    fun isFinancialOffenseAllowed(): Boolean {
        return currentState == CombatState.OP || currentState == CombatState.ENGAGED
    }

    /**
     * Déclenche l'exfiltration prioritaire vers le HQ.
     * Uniquement en mode ENGAGED.
     */
    fun forceImmediateExfiltration(): Boolean = currentState == CombatState.ENGAGED

    /**
     * Retourne la liste des capacités débloquées pour l'UI.
     */
    private fun getActiveCapabilities(): List<String> {
        return when (currentState) {
            CombatState.STBY -> listOf("CORE_SECURITY")
            CombatState.RECO -> listOf("CORE_SECURITY", "BFT_TRACKING", "PASSIVE_SIGINT")
            CombatState.OP -> listOf("CORE_SECURITY", "BFT_TRACKING", "ACTIVE_SIGINT", "FIN_CAPACITY")
            CombatState.ENGAGED -> listOf("CORE_SECURITY", "BFT_TRACKING", "ACTIVE_SIGINT", "FIN_CAPACITY", "REALTIME_REPORTING")
        }
    }

    fun getStatus(): CombatState = currentState
}
