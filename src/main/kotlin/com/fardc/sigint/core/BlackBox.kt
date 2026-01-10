package com.fardc.sigint.core

class BlackBox {
    fun recordIncident(type: String, message: String) {
        println("[📝] LOG [$type] : $message")
    }
}
