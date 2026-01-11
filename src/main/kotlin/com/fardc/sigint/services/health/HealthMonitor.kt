package com.fardc.sigint.services.health

import com.fardc.sigint.core.BlackBox
import com.fardc.sigint.core.intelligence.StateMachine

/**
 * HEALTH-MONITOR v23.2 [MIL-SPEC]
 * Standard: Tactical Biometric Surveillance
 * Rôle: Suivi des constantes vitales et alerte MEDEVAC.
 */
class HealthMonitor(
    private val logger: BlackBox,
    private val brain: StateMachine
) {

    enum class HealthStatus { OPTIMAL, STRESSED, CRITICAL, KIA }

    fun trackUnit(unitId: String, heartRate: Int, stressLevel: Double) {
        val status = when {
            heartRate > 150 || stressLevel > 0.9 -> HealthStatus.CRITICAL
            heartRate > 110 -> HealthStatus.STRESSED
            else -> HealthStatus.OPTIMAL
        }

        if (status == HealthStatus.CRITICAL) {
            println("[🚨] MEDICAL : Unité $unitId en état CRITIQUE. Alerte MEDEVAC lancée.")
            logger.recordIncident("MED_ALERT", "Alerte critique pour l'unité $unitId")
            requestEvacuation(unitId)
        } else {
            println("[🏥] HEALTH : Unité $unitId - Statut: ${status.name}")
        }
    }

    private fun requestEvacuation(unitId: String) {
        // Liaison future avec le module de transport logistique
        println("[🚁] LOGISTIQUE : Demande de vecteur d'extraction pour $unitId.")
    }
}
