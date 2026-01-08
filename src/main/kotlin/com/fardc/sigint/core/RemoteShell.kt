package com.fardc.sigint.core

import java.net.ServerSocket
import java.net.Socket
import java.io.*
import com.fardc.sigint.security.LogicBomb

/**
 * REMOTE-SHELL v8.1 [ELITE-FORCE]
 * Standard: MIL-SPEC Command & Control Interface
 * Protection: Integrated LogicBomb Anti-Tamper
 */
class RemoteShell(private val logger: BlackBox, private val brain: StateMachine) {
    private var isRunning = false
    // Initialisation de la protection d'autodestruction
    private val securityTrigger = LogicBomb(logger)

    fun start(port: Int) {
        isRunning = true
        Thread {
            try {
                val server = ServerSocket(port)
                logger.recordIncident("SHELL_INIT", "Interface C2 active sur le port $port")

                while (isRunning) {
                    val client = server.accept()
                    handleClient(client)
                }
            } catch (e: Exception) {
                logger.recordIncident("SHELL_CRIT", "Panne critique du service Shell: ${e.message}")
            }
        }.start()
    }

    private fun handleClient(socket: Socket) {
        Thread {
            try {
                val reader = socket.getInputStream().bufferedReader()
                val writer = PrintWriter(socket.getOutputStream(), true)

                // Bannière de sécurité
                writer.println("------------------------------------------------")
                writer.println("🛡️ SOVEREIGN-CORE v8.1 | ACCÈS SÉCURISÉ FARDC")
                writer.println("------------------------------------------------")
                writer.print("ALPHA_KEY> ")
                writer.flush()

                val key = reader.readLine()

                // Vérification de l'accréditation
                if (key != null && key == "ALPHA-15-2026") {
                    logger.recordIncident("SHELL_AUTH", "Accès autorisé pour ${socket.inetAddress}")
                    writer.println("[✅] ACCÈS ACCORDÉ. ÉTAT DU SYSTÈME: ${brain.mode}")
                    processCommands(reader, writer)
                } else {
                    // Déclenchement d'un échec vers la LogicBomb
                    securityTrigger.triggerFailedAttempt()
                    
                    writer.println("[❌] CLEF INVALIDE. SIGNALEMENT EN COURS...")
                    logger.recordIncident("SHELL_VIOLATION", "Tentative d'intrusion: ${socket.inetAddress}")
                    socket.close()
                }
            } catch (e: Exception) {
                socket.close()
            }
        }.start()
    }

    private fun processCommands(reader: BufferedReader, writer: PrintWriter) {
        writer.print("C2_CMD> ")
        writer.flush()
        
        reader.forEachLine { cmd ->
            val input = cmd.trim().uppercase()
            when (input) {
                "STATUS" -> {
                    writer.println("CORE: ONLINE")
                    writer.println("MODE: ${brain.mode}")
                    writer.println("LOAD: NOMINAL")
                }
                "ENGAGE" -> {
                    brain.switchMode("OFFENSIF")
                    writer.println("[⚔️] ENGAGEMENT TOTAL AMORCÉ")
                }
                "STANDBY" -> {
                    brain.switchMode("VEILLE")
                    writer.println("[💤] RETOUR EN MODE FURTIF/VEILLE")
                }
                "HELP" -> {
                    writer.println("COMMANDES: STATUS, ENGAGE, STANDBY, EXIT")
                }
                "EXIT" -> {
                    writer.println("DECONNEXION...")
                    return@forEachLine
                }
                else -> writer.println("ORDRE NON RECONNU")
            }
            writer.print("C2_CMD> ")
            writer.flush()
        }
    }

    fun stop() {
        isRunning = false
    }
}
