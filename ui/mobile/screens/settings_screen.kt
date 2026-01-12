package com.fardc.sigint.ui.mobile.screens

import com.fardc.sigint.core.BlackBox
import com.fardc.sigint.security.SecurityVault
import com.fardc.sigint.core.hardware.SovereignHAL

/**
 * SOVEREIGN-SETTINGS-SCREEN v28.2
 * Rôle: Gestion du profil de mission et de la furtivité électronique.
 */
class SettingsScreen(
    private val logger: BlackBox,
    private val vault: SecurityVault,
    private val hal: SovereignHAL
) {

    enum class StealthLevel { BLACK_OUT, LOW_OBSERVABILITY, FULL_TACTICAL }

    fun applyStealthProfile(level: StealthLevel) {
        println("[⚙️] SETTINGS : Application du profil de furtivité -> $level")
        
        when(level) {
            StealthLevel.BLACK_OUT -> {
                println("[📵] EMCON : Désactivation totale des émetteurs (Mode Fantôme).")
                hal.triggerHardwareAction("RADIO_OFF", mapOf("mode" to "silent"))
            }
            StealthLevel.LOW_OBSERVABILITY -> {
                println("[📡] EMCON : Réduction de la puissance d'émission Mesh.")
            }
            StealthLevel.FULL_TACTICAL -> {
                println("[🌐] EMCON : Puissance maximale pour synchronisation MDO.")
            }
        }
        logger.recordIncident("CONFIG_CHANGE", "Profil furtivité mis à jour: $level")
    }

    fun purgeSensitiveData() {
        println("[🔥] SETTINGS : Activation du protocole de purge immédiate...")
        vault.engage() // Appelle le scellage/destruction des clés
        logger.recordIncident("MANUAL_PURGE", "L'utilisateur a déclenché l'effacement des données.")
    }
}

