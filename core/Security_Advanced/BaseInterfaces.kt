package com.fardc.sigint.core
enum class CombatState { STBY, OP, ENGAGED }
interface CombatModeHandlerInterface {
    fun getStatus(): CombatState
    fun isBFTOperational(): Boolean
    fun isFinancialOffenseAllowed(): Boolean
    fun isPassiveInterceptionEnabled(): Boolean
}
