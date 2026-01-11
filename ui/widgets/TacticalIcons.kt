package com.fardc.sigint.ui.widgets

/**
 * SOVEREIGN-TACTICAL-ICONS v27.3
 * Standard: MIL-STD-2525D / NATO APP-6
 * Rôle: Génération de la symbologie de combat Multi-Domaines.
 */
class TacticalIcons {

    enum class Affiliation { FRIENDLY, HOSTILE, NEUTRAL, UNKNOWN }
    enum class UnitType { INFANTRY, ARMOR, DRONE, SIGINT_SOURCE, ARTILLERY }

    data class IconDefinition(
        val affiliation: Affiliation,
        val type: UnitType,
        val isMoving: Boolean,
        val alertLevel: Int // 0 to 5
    )

    fun getIconVector(def: IconDefinition): String {
        val color = when (def.affiliation) {
            Affiliation.FRIENDLY -> "#0000FF" // Bleu FARDC
            Affiliation.HOSTILE -> "#FF0000"  // Rouge Menace
            else -> "#FFFF00"                 // Jaune Inconnu
        }
        
        println("[🎨] ICON-GEN : Génération icône ${def.type} (${def.affiliation}) - Alerte: ${def.alertLevel}")
        return "SVG_DATA_STREAM_FOR_${def.type}"
    }
}

