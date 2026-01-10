package com.fardc.sigint.core

enum class EngagementMode {
    SILENT_WATCH,    // Surveillance passive (SIGINT) - Zéro émission
    DEFENSIVE_AUTO,  // Alerte drone - Brouillage réactif si menace détectée
    ELECTRONIC_ASSAULT // Brouillage de barrage total - Puissance max
}

class CombatModeSelector {
    var currentMode = EngagementMode.SILENT_WATCH

    fun setMode(mode: String): String {
        currentMode = when(mode.uppercase()) {
            "SILENT" -> EngagementMode.SILENT_WATCH
            "AUTO" -> EngagementMode.DEFENSIVE_AUTO
            "ASSAULT" -> EngagementMode.ELECTRONIC_ASSAULT
            else -> EngagementMode.SILENT_WATCH
        }
        return "POSTURE_MODIFIED: ${currentMode.name}"
    }
}
