package com.fardc.sigint.core

// Interfaces de grade militaire pour la stabilité du build
enum class CombatState { STBY, OP, ENGAGED }

interface CombatModeHandler {
    fun getStatus(): CombatState
    fun isBFTOperational(): Boolean
}

interface SecurityVault {
    fun encryptData(data: String)
}
