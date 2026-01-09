package com.fardc.sigint.network

import com.fardc.sigint.core.BlackBox
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

/**
 * SIGNAL-STREAMER v8.1 (Purified)
 * Standard: Low-Latency UDP Tactical Stream
 * Rôle: Diffusion temps-réel du spectre et des captures SIGINT
 */
class SignalStreamer(private val logger: BlackBox) {
    private var socket: DatagramSocket? = null
    private val targetPort = 15500
    private var isStreaming = false

    fun initStream() {
        try {
            socket = DatagramSocket()
            isStreaming = true
            logger.recordIncident("STREAM_INIT", "Serveur de diffusion UDP actif sur port $targetPort")
        } catch (e: Exception) {
            logger.recordIncident("STREAM_ERR", "Échec d'initialisation du canal de diffusion")
        }
    }

    fun broadcastData(payload: String, address: String = "255.255.255.255") {
        if (!isStreaming || socket == null) return

        Thread {
            try {
                val data = payload.toByteArray()
                val packet = DatagramPacket(
                    data, 
                    data.size, 
                    InetAddress.getByName(address), 
                    targetPort
                )
                socket?.send(packet)
            } catch (e: Exception) {
                // Échec silencieux pour maintenir la furtivité
            }
        }.start()
    }

    fun stopStream() {
        isStreaming = false
        socket?.close()
    }
}
