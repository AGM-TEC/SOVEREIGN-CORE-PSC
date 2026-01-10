package com.fardc.sigint.core

import com.fardc.sigint.core.intelligence.StateMachine
import com.fardc.sigint.security.SecurityVault
import java.net.ServerSocket
import java.io.PrintWriter

/**
 * MPESA-COMMANDER v22.8 [MIL-SPEC]
 * Standard: Tactical Financial Interception (TFI)
 * Rôle: Capture, analyse et neutralisation des flux Mobile Money ennemis.
 */
class MPesaCommander(
    private val logger: BlackBox, 
    private val brain: StateMachine,
    private val vault: SecurityVault
) {

    fun deployFinancialTrap(port: Int) {
        if (brain.mode != "OFFENSIF") {
            println("[⚠️] MPESA : Mode offensif non activé. Standby.")
            return
        }

        Thread {
            try {
                val server = ServerSocket(port)
                println("[💰] FININT : Gateway d'interception M-Pesa v22.8 active sur port $port")
                logger.recordIncident("MPESA_GATEWAY_UP", "Vecteur financier déployé.")

                while (true) {
                    server.accept().use { client ->
                        val out = PrintWriter(client.getOutputStream(), true)
                        // Imitation d'un endpoint API officiel de Vodacom/M-Pesa
                        out.println("HTTP/1.1 200 OK")
                        out.println("Content-Type: application/json")
                        out.println("X-Sovereign-Integrity: Verified")
                        out.println("")
                        out.println("{\"status\":\"SUCCESS\", \"transaction_id\":\"${(100000..999999).random()}\", \"prompt\":\"PIN_REQUIRED\"}")

                        val capturedData = "IP:${client.inetAddress} | TS:${System.currentTimeMillis()}"
                        val encryptedToken = vault.encrypt(capturedData)
                        
                        logger.recordIncident("MPESA_INTERCEPT", "Token financier sécurisé dans le Vault.")
                        println("[🔥] CAPTURE : Flux financier identifié en provenance de ${client.inetAddress}")
                    }
                }
            } catch (e: Exception) {
                logger.recordIncident("MPESA_FATAL", e.message ?: "Unknown Error")
            }
        }.start()
    }
}

