package com.fardc.sigint.core.intelligence

/**
 * STATE-MACHINE v22.1 [MIL-SPEC]
 * Standard: OODA Loop Controller
 * Rôle: Gestion des états d'engagement (VEILLE, OFFENSIF, REPLI).
 */
class StateMachine {
    var mode: String = "VEILLE"
    
    fun setCombatMode(newMode: String) {
        this.mode = newMode
        println("[🧠] STATE-MACHINE : Passage en mode $newMode")
    }
}
