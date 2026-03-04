package core.network

import core.security.BlackBox
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.util.concurrent.Executors

class SignalStreamer(private val logger: BlackBox) {
    private var socket: DatagramSocket? = null
    private val targetPort = 15500
    private var isStreaming = false

    // Exécuteur asynchrone pour bloquer la surcharge CPU (Thermal Throttling)
    private val executor = Executors.newSingleThreadExecutor()

    fun initStream() {
        try {
            socket = DatagramSocket()
            socket?.broadcast = true 
            isStreaming = true
            logger.recordIncident("STREAM_INIT", "Serveur UDP actif sur port $targetPort")
        } catch (e: Exception) {
            logger.recordIncident("STREAM_ERR", "Échec d'initialisation du socket UDP")
        }
    }

    fun broadcastData(payload: ByteArray, address: String = "192.168.43.255") {
        if (!isStreaming || socket == null) return

        executor.submit {
            try {
                val packet = DatagramPacket(
                    payload, 
                    payload.size, 
                    InetAddress.getByName(address), 
                    targetPort
                )
                socket?.send(packet)
            } catch (e: Exception) {
                // Furtivité : Échec silencieux
            }
        }
    }

    fun stopStream() {
        isStreaming = false
        socket?.close()
        executor.shutdown()
    }
}
