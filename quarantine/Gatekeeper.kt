package com.fardc.sigint.core

class Gatekeeper(private val brain: StateMachine, private val logger: BlackBox) {
    // Liste des processus souverains autorisés
    private val authorizedProcesses = setOf(
        "SIGINT_CORE", 
        "BFT_MODULE", 
        "CYBER_FIN_CAP", 
        "DATA_PURGE_ENGINE",
        "ENCRYPTION_BRIDGE"
    )

    fun validateAccess(processName: String): Boolean {
        println("[GATEKEEPER] Analyse de la requête pour : $processName")

        // 1. Vérification de l'identité du processus
        if (!authorizedProcesses.contains(processName)) {
            logger.record_incident("UNAUTHORIZED_ACCESS", "Tentative d'accès par : $processName")
            println("[🚫] ALERTE : Accès refusé pour processus non certifié.")
            return false
        }

        // 2. Vérification de la posture de combat
        if (brain.current_mode == "FURTIF" && processName == "SIGINT_CORE") {
            println("[⚠️] ACCÈS RESTREINT : SIGINT désactivé en mode FURTIF pour discrétion.")
            return false
        }

        println("[✅] ACCÈS ACCORDÉ : $processName validé.")
        return true
    }
}
