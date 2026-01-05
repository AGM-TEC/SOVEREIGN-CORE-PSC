package com.fardc.sigint.core.security

class GhostMode {
    companion object {
        var isEnabled: Boolean = true
        
        fun log(message: String) {
            // Solo se permite la salida si son datos de captura crítica
            if (message.contains("[💰]") || message.contains("[🚨]")) {
                println(message)
            }
            // El resto de la telemetría se descarta silenciosamente
        }
    }
}
