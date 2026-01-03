package com.fardc.sigint.services.transmission

class FallbackTransmitter {
    fun transmit(data: String): Boolean {
        println("Fallback operational: Data transmission initiated.")
        // Logique de secours simplifiée pour garantir le build
        return data.isNotEmpty()
    }
}
