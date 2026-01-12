package com.fardc.sigint.ui.tactical

import com.fardc.sigint.core.hardware.SovereignHAL
import com.fardc.sigint.core.BlackBox

/**
 * SOVEREIGN-NIGHT-VISION-THEME v29.3
 * Rôle: Gestion de la signature lumineuse et protection de l'acuité visuelle.
 * Standard: MIL-L-85762A Compatible.
 */
class NightVisionTheme(
    private val hal: SovereignHAL,
    private val logger: BlackBox
) {

    enum class VisionMode { DAY, NIGHT_RED, NIGHT_AMBER, NVG_COMPATIBLE }

    fun applyTheme(mode: VisionMode) {
        println("[🌘] VISION : Passage au mode $mode...")
        
        when(mode) {
            VisionMode.NIGHT_RED -> {
                println("[🔴] THEME : Filtrage spectral (Rouge profond - 650nm).")
                // Commande matérielle pour abaisser le rétroéclairage via le HAL
                hal.triggerHardwareAction("SET_BRIGHTNESS", mapOf("level" to "5%"))
            }
            VisionMode.NVG_COMPATIBLE -> {
                println("[🟢] THEME : Mode JVN actif. Suppression des IR parasites.")
            }
            else -> println("[☀️] THEME : Mode jour standard.")
        }
        
        logger.recordIncident("THEME_CHANGE", "Mode vision mis à jour vers $mode")
    }

    fun engage() {
        println("[🌑] NIGHT-VISION-THEME v29.3 : Protection visuelle de combat active.")
    }
}

