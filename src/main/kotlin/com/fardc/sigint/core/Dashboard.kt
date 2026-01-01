package com.fardc.sigint.core
import io.javalin.Javalin

class Dashboard {
    fun setup(app: Javalin) {
        app.get("/") { ctx ->
            ctx.html("""
                <html>
                <body style='background:#0a0a0a; color:#00ff00; font-family:monospace; padding:20px;'>
                    <h1 style='border-bottom:2px solid #00ff00;'>🛡️ FARDC - SOVEREIGN CORE v2.4</h1>
                    <p><b>ÉTAT DU SYSTÈME :</b> <span style='color:white;'>OPÉRATIONNEL</span></p>
                    <p><b>MODE COMBAT :</b> <span style='color:red;'>ACTIF</span></p>
                    <div style='background:#1a1a1a; padding:10px; border:1px solid #333;'>
                        <h3>SÉCURITÉ :</h3>
                        <ul><li>CHIFFREMENT : AES-256</li><li>RÉSEAU MESH : SYNCHRONISÉ</li></ul>
                    </div>
                </body>
                </html>
            """)
        }
    }
}
