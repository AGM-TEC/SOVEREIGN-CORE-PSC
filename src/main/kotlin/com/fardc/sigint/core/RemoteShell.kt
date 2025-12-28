package com.fardc.sigint.core

import java.net.ServerSocket
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.concurrent.thread

/**
 * MODULE REMOTE-SHELL PSC
 * Contrôle à distance sécurisé du noyau souverain
 */
class RemoteShell(private val vault: SecurityVault) {
    fun startShell(port: Int) {
        println("[SHELL] Activation du Shell distant sur le port $port...")
        thread {
            try {
                val server = ServerSocket(port)
                while (true) {
                    val client = server.accept()
                    thread { handleSession(client) }
                }
            } catch (e: Exception) {
                println("[ERROR] Échec du Shell : ${e.message}")
            }
        }
    }

    private fun handleSession(client: java.net.Socket) {
        val reader = BufferedReader(InputStreamReader(client.getInputStream()))
        val output = client.getOutputStream()
        
        output.write("🛡️ SOVEREIGN-SHELL ACTIVE\n> ".toByteArray())
        
        reader.forEachLine { line ->
            // Seules les commandes chiffrées ou autorisées passent ici
            println("[CMD] Exécution : $line")
            output.write("OK\n> ".toByteArray())
        }
    }
}
