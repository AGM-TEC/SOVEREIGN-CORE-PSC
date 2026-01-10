package com.fardc.sigint.network
import com.fardc.sigint.core.BlackBox
class NetworkEngine(private val logger: BlackBox) {
    fun setInterfaceMode(iface: String, mode: String) {
        println("[📡] NETWORK : Configuration de $iface en mode $mode...")
    }
}
