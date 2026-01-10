package com.fardc.sigint.core
import com.fardc.sigint.security.LogicBomb
import java.net.ServerSocket

class RemoteShell(private val logger: BlackBox, private val brain: StateMachine) {
    fun start(port: Int) {
        Thread {
            try {
                val server = ServerSocket(port)
                while (true) {
                    val client = server.accept()
                    // Logique d'auth simplifiée pour la forge
                    println("[SHELL] Connexion entrante...")
                }
            } catch (e: Exception) {}
        }.start()
    }
}
