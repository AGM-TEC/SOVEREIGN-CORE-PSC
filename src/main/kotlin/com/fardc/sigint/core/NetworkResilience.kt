package com.fardc.sigint.core
import java.net.ServerSocket
import kotlin.concurrent.thread
class NetworkResilience {
    fun monitorSystem() {
        println("[CORE] Écoute active sur le port 8888...")
        thread { try { ServerSocket(8888).accept() } catch (e: Exception) {} }
    }
}
