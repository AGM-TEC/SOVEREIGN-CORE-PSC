package com.fardc.sigint.services.dsp

import com.fardc.sigint.core.CombatModeHandler
import com.fardc.sigint.core.CombatState
import com.fardc.sigint.core.SecurityVault
import java.time.Instant

class SignalClassifier(private val vault: SecurityVault, private val combatHandler: CombatModeHandler) {
    fun processInference(data: ByteArray): Map<String, Any> {
        if (combatHandler.getStatus() == CombatState.STBY) return mapOf("status" to "LOCKED")
        return mapOf("modulation" to "DMR_DETECTED", "anomaly" to true, "timestamp" to Instant.now().toString())
    }
}
