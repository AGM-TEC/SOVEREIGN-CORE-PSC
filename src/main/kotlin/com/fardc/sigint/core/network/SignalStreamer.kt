package core.network

import core.security.BlackBox
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.util.concurrent.Executors

/**
 * SIGNAL-STREAMER v33.1 [MIL-SPEC]
 * Standard: Flux UDP asynchrone optimisé pour Edge Computing (Termux)
 * Rôle: Diffusion temps-réel du spectre avec prévention de surcharge CPU.
 */
class SignalStreamer(private val logger: BlackBox) {
    private var socket: DatagramSocket? = null
    private val targetPort = 15500
    private var isStreaming = false

    // Pivot 1 : Utilisation d'un exécuteur à fil unique (Single Thread Executor)
    // Empêche la création exponentielle de threads et gère une file d'attente (Queue) FIFO.
    private val executor = Executors.newSingleThreadExecutor()

    fun initStream() {
        try {
            socket = DatagramSocket()
            // Pivot 2 : Autorisation explicite au niveau du noyau OS pour l'émission en rafale
            socket?.broadcast = true 
            isStreaming = true
            logger.recordIncident("STREAM_INIT", "Serveur UDP actif sur port $targetPort")
        } catch (e: Exception) {
            logger.recordIncident("STREAM_ERR", "Échec d'initialisation du socket UDP")
        }
    }

    // Pivot 3 : Traitement direct des octets (ByteArray) pour le flux SIGINT brut
    // L'adresse par défaut doit être le sous-réseau local (ex: 192.168.43.255) et non le global.
    fun broadcastData(payload: ByteArray, address: String = "192.168.43.255") {
        if (!isStreaming || socket == null) return

        // Soumission de la tâche à la file d'attente existante (Zéro surcharge de création)
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
                // Rejet silencieux pour maintenir la furtivité radio
            }
        }
    }

    fun stopStream() {
        isStreaming = false
        socket?.close()
        executor.shutdown()
    }
}
