package com.fardc.sigint.core

import java.io.File
import java.time.Instant

class MissionReporter(private val vault: SecurityVault, private val logger: BlackBox) {
    
    init {
        File("reports").mkdirs() // Garantie l'existence du dossier
    }

    fun logAction(action: String) {
        val entry = "[${Instant.now()}] $action\n"
        // On journalise localement et dans la BlackBox pour redondance
        logger.record_incident("MISSION_EVENT", action)
        File("reports/mission.log").appendText(entry)
    }

    fun generateDailyReport() {
        val incidentCount = logger.get_incident_count() // Nécessite l'ajout de cette méthode
        val reportContent = """
            🛡️ RAPPORT TACTIQUE SOVEREIGN CORE
            DATE : ${Instant.now()}
            UNITÉ : FARDC-SIGINT-ALPHA
            ----------------------------------
            ACTIVITÉ TOTALE : $incidentCount événements enregistrés
            DERNIER INCIDENT : ${logger.get_last_incident() ?: "NÉANT"}
            STATUS : OPÉRATIONNEL SÉCURISÉ
        """.trimIndent()

        // CHIFFREMENT AVANT ÉCRITURE
        val encryptedReport = vault.encryptData(reportContent)
        
        val fileName = "reports/daily_report_${Instant.now().epochSecond}.enc"
        File(fileName).writeText(encryptedReport)
        
        println("📊 [REPORTER] Rapport chiffré généré : $fileName")
    }
}
