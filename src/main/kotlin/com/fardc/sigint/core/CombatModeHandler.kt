package com.fardc.sigint.core
enum class CombatState { STBY, ACTIVE, ENGAGED }
class CombatModeHandler {
    fun getStatus(): CombatState = CombatState.ACTIVE
    fun isBFTOperational(): Boolean = true
    fun isFinancialOffenseAllowed(): Boolean = true
    fun isPassiveInterceptionEnabled(): Boolean = true
}
