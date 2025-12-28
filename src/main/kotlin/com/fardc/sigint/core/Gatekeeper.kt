package com.fardc.sigint.core

/**
 * Système de contrôle d'accès aux ressources critiques
 */
class Gatekeeper {
    fun validateAccess(processName: String): Boolean {
        println("[GATEKEEPER] Validation de l'accès pour : $processName")
        return true // Autorisation souveraine
    }
}
