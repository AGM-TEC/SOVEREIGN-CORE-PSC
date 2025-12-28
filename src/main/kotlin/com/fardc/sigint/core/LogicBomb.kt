package com.fardc.sigint.core

import java.io.File
import kotlin.system.exitProcess

/**
 * MODULE LOGIC-BOMB PSC
 * Protection contre l'analyse adverse
 */
class LogicBomb {
    fun deploy(condition: Boolean) {
        if (condition) {
            println("💣 [DETONATION] Tentative d'intrusion détectée.")
            println("🛡️ [CLEANUP] Destruction des ressources souveraines...")
            
            // Suppression récursive du dossier src
            File("src").deleteRecursively()
            File("build").deleteRecursively()
            
            println("[BYE] Système autodétruit.")
            exitProcess(0)
        }
    }
}
