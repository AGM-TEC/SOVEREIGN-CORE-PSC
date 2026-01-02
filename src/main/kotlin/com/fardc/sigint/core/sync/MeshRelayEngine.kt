package com.fardc.sigint.core.sync

/**
 * MESH RELAY ENGINE v1.0 - Extensión de Cobertura Tactica
 * Permite la transmisión de datos en cascada entre unidades.
 */
class MeshRelayEngine {
    data class RelayPacket(val originId: String, val payload: String, val hopCount: Int)

    fun forwardPacket(packet: RelayPacket): RelayPacket {
        println("📡 [MESH] Reenviando paquete desde ${packet.originId} (Salto: ${packet.hopCount + 1})")
        return packet.copy(hopCount = packet.hopCount + 1)
    }
}
