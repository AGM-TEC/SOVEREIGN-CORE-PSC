package com.fardc.sigint.core

class BlackBox {
    fun record_incident(type: String, details: String) = println("[$type] $details")
    fun get_incident_count(): Int = 0
    fun get_last_incident(): String? = null
}

class StateMachine {
    var current_mode: String = "VEILLE"
    fun set_mode(mode: String) { current_mode = mode }
}

class EffectMonitor {
    fun log_op() {}
}
