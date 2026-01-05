package com.fardc.sigint.core
class CombatModeHandler : CombatModeHandlerInterface {
    override fun getStatus(): CombatState = CombatState.OP
    override fun isBFTOperational(): Boolean = true
    override fun isFinancialOffenseAllowed(): Boolean = true
    override fun isPassiveInterceptionEnabled(): Boolean = true
}
