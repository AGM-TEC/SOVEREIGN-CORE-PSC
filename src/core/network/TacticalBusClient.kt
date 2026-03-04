package core.network

import org.zeromq.SocketType
import org.zeromq.ZContext
import core.hardware.SovereignHAL

class TacticalBusClient : Runnable {
    @Volatile private var isRunning = true

    override fun run() {
        ZContext().use { context ->
            val socket = context.createSocket(SocketType.SUB)
            socket.connect("tcp://127.0.0.1:50051")
            socket.subscribe(byteArrayOf())
            println("[SYSTEM] Nœud Kotlin connecté au bus ZeroMQ.")

            while (isRunning && !Thread.currentThread().isInterrupted) {
                val payload = socket.recvStr(0)
                if (payload != null) {
                    processTacticalData(payload)
                }
            }
        }
    }

    private fun processTacticalData(payload: String) {
        println("[SIGINT INTERCEPT] $payload")
        
        // Extraction rudimentaire de la fréquence pour la preuve de concept
        if (payload.contains("DRONE_HOSTILE_DETECTE")) {
            val freqMatch = Regex("FREQ: ([0-9.]+) MHz").find(payload)
            val frequency = freqMatch?.groupValues?.get(1)?.toDoubleOrNull() ?: 0.0
            
            // Routage de la décision vers la couche d'abstraction matérielle
            SovereignHAL.engageTarget("DRONE_HOSTILE", frequency)
        }
    }

    fun shutdown() {
        isRunning = false
    }
}
