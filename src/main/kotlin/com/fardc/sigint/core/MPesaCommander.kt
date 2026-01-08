package com.fardc.sigint.core

import java.net.ServerSocket
import java.io.PrintWriter

/**
 * MPESA-COMMANDER v8.1 (Purified)
 * Standard: Standalone Military Grade
 * Vecteur: Capture de tokens M-Pesa via Gateway simulée
 */
class MPesaCommander(private val logger: BlackBox, private val brain: StateMachine) {

    fun initiateGateway(port: Int) {
        if (brain.mode != "OFFENSIF") return

        Thread {
            try {
                val server = ServerSocket(port)
                logger.recordIncident("MPESA_INIT", "Gateway financière active sur port $port")
                
                while (true) {
                    val client = server.accept()
                    // Simulation d'une réponse API M-Pesa (JSON)
                    val out = PrintWriter(client.getOutputStream(), true)
                    out.println("HTTP/1.1 200 OK")
                    out.println("Content-Type: application/json")
                    out.println("")
                    out.println("{\"status\":\"AWAIT_TOKEN\", \"instruction\":\"INPUT_PIN_ON_MOBILE\"}")
                    
                    logger.recordIncident("MPESA_HIT", "Interception de requête depuis ${client.inetAddress}")
                    client.close()
                }
            } catch (e: Exception) {
                logger.recordIncident("MPESA_ERR", e.message ?: "Gateway Error")
            }
        }.start()
    }
}
