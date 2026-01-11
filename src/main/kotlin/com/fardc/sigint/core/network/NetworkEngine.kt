package com.fardc.sigint.core.network
import com.fardc.sigint.core.BlackBox
class NetworkEngine(private val logger: BlackBox) {
    fun engage() = println("[📡] NET-CORE : Liaison Multi-Path active.")
    fun transmit(payload: String) {
        println("[🛰️] TX : Transmission du paquet MDO : $payload")
    }
}
