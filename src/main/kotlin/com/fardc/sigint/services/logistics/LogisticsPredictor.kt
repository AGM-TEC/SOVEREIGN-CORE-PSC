package com.fardc.sigint.services.logistics

import com.fardc.sigint.core.BlackBox

/**
 * LOGISTICS-PREDICTOR v9.1 [MIL-SPEC]
 * Standard: Predictive Supply Chain Management
 * Rôle: Calcul des besoins en ressources pour les OPLANs.
 */
class LogisticsPredictor(private val logger: BlackBox) {

    data class Resources(val ammoUnits: Int, val fuelLiters: Int, val rations: Int)

    fun forecastNeeds(durationDays: Int, intensity: String): Resources {
        println("[📦] LOGISTIQUE : Calcul des besoins pour $durationDays jours ($intensity intensity)...")
        
        val multiplier = if (intensity == "HIGH") 3 else 1
        val needs = Resources(
            ammoUnits = 5000 * durationDays * multiplier,
            fuelLiters = 200 * durationDays * multiplier,
            rations = 100 * durationDays
        )

        println("[📊] PROJECTION : Munitions: ${needs.ammoUnits} | Carburant: ${needs.fuelLiters}L | Rations: ${needs.rations}")
        logger.recordIncident("LOG_FORECAST", "Besoins calculés pour OP: $intensity")
        return needs
    }
}
