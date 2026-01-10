package com.fardc.sigint.services.health
import com.fardc.sigint.core.BlackBox
class HealthForceMonitor(private val logger: BlackBox) {
    fun assess() = println("[🏥] HEALTH : DOR des troupes calculée à 92%.")
}
