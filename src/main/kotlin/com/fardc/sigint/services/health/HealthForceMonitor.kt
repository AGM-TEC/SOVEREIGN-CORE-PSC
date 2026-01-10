package com.fardc.sigint.services.health

import com.fardc.sigint.core.BlackBox

/**
 * HEALTH-FORCE-MONITOR v9.3 [MIL-SPEC]
 * Standard: NATO STANAG 2122 / Ratnik-Health Compliant
 * Rôle: Surveillance biométrique et calcul de la disponibilité opérationnelle (DOR).
 */
class HealthForceMonitor(private val logger: BlackBox) {

    enum class HealthStatus { OPTIMAL, FATIGUED, CRITICAL, KIA }

    data class UnitHealth(
        val unitName: String,
        val effectiveStrength: Int,
        val averageFatigue: Int, // 0-100
        val status: HealthStatus
    )

    fun calculateOperationalAvailability(unit: UnitHealth): Double {
        println("[🏥] HEALTH-MONITOR : Calcul de la DOR pour ${unit.unitName}...")
        
        // Algorithme de dégradation : Santé physique + Fatigue psychologique
        val dor = (unit.effectiveStrength.toDouble() / 100.0) * (1.0 - (unit.averageFatigue / 200.0))
        
        val recommendation = when {
            dor > 0.8 -> "UNITÉ PRÊTE POUR ASSAUT."
            dor > 0.5 -> "SOUTIEN REQUIS. LIMITER L'ENGAGEMENT."
            else -> "UNITÉ NON OPÉRATIONNELLE. REPLI IMMÉDIAT."
        }
        
        println("[📊] RÉSULTAT DOR : ${"%.2f".format(dor)} | $recommendation")
        logger.recordIncident("HEALTH_DOR", "DOR ${unit.unitName} : $dor")
        return dor
    }
}
