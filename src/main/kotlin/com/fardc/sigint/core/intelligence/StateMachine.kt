package com.fardc.sigint.core.intelligence

class StateMachine {
    var mode: String = "STANDBY"
    fun transitionTo(newMode: String) {
        this.mode = newMode
        println("[🧠] STATE-MACHINE : Transition vers le mode $newMode")
    }
}
