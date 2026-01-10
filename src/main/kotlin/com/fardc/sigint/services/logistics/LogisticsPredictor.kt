package com.fardc.sigint.services.logistics
import com.fardc.sigint.core.BlackBox

class LogisticsPredictor(private val logger: BlackBox) {
    fun forecastNeeds(days: Int, intensity: String) = println("[📦] LOG : Besoins calculés pour $days jours ($intensity).")
}
