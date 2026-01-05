package com.fardc.sigint.core.interop

import com.fardc.sigint.core.security.SecurityController
import com.fardc.sigint.core.security.HackBackEngine

class MissionOrchestrator(
    private val security: SecurityController,
    private val hackBack: HackBackEngine
) {
    fun launchSovereignShield() {
        security.checkIntegrity()
        hackBack.monitorIncoming()
        println("[✅] ORCHESTRATOR : Système en ligne (Grade Étatique).")
    }
}
