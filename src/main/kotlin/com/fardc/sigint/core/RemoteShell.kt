package com.fardc.sigint.core

import java.net.ServerSocket
import java.net.Socket
import java.io.*

/**
 * REMOTE-SHELL v8.1 (Purified)
 * Standard: MIL-SPEC Secure Command Interface
 */
class RemoteShell(private val logger: BlackBox, private val brain: StateMachine) {
    private var isRunning = false

    fun start(port: Int) {
        isRunning = true
        Thread {
            try {
                val server = ServerSocket(port)
                logger.recordIncident("SHELL_INIT", "Interface de commande distante active sur port $port")
                
                while (isRunning) {
                    val client = server.accept()
                    handleClient(client)
                }
            } catch (e: Exception) {
                logger.recordIncident("SHELL_CRIT", "Erreur fatale Shell : ${e.message}")
            }
        }.start()
    }

    private fun handleClient(socket: Socket) {
        Thread {
            try {
                val reader = socket.getInputStream().bufferedReader()
                val writer = PrintWriter(socket.getOutputStream(), true)

                writer.println("🛡️ SOVEREIGN-CORE v8.1 [AUTH REQUIRED]")
                writer.print("AUTH_KEY> ")
                writer.flush()

                val key = reader.readLine()
                // Vérification basique pour l'audit (sera renforcée par le Vault)
                if (key == "ALPHA-15-2026") {
                    writer.println("[✅] ACCÈS ACCORDÉ. MODE ACTUEL: ${brain.mode}")
                    processCommands(reader, writer)
                } else {
                    logger.recordIncident("SHELL_UNAUTH", "Tentative d'accès depuis ${socket.inetAddress}")
                    writer.println("[❌] ACCÈS REFUSÉ.")
                    socket.close()
                }
            } catch (e: Exception) {}
        }.start()
    }

    private fun processCommands(reader: BufferedReader, writer: PrintWriter) {
        writer.print("CMD> ")
        writer.flush()
        reader.forEachLine { cmd ->
            when (cmd.uppercase()) {
                "STATUS" -> writer.println("SYSTEM_OK | MODE: ${brain.mode}")
                "ENGAGE" -> {
                    brain.switchMode("OFFENSIF")
                    writer.println("ORDRE REÇU: ENGAGEMENT TOTAL")
                }
                "EXIT" -> return@forEachLine
                else -> writer.println("COMMANDE INCONNUE OU RESTREINTE")
            }
            writer.print("CMD> ")
            writer.flush()
        }
    }
}
