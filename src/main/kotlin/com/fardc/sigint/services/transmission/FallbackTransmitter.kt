package com.fardc.sigint.services.transmission

import com.fardc.sigint.core.CombatModeHandler
import com.fardc.sigint.core.CombatState
import com.fardc.sigint.core.SecurityVault

class FallbackTransmitter(private val vault: SecurityVault, private val combatHandler: CombatModeHandler) {
    fun transmitEmergency(payload: String): Boolean {
        if (combatHandler.getStatus() == CombatState.OP) {
            println("📻 [HF_TX] Emergency broadcast active")
            return true
        }
        return false
    }
}
