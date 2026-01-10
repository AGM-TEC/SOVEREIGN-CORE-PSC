package com.fardc.sigint.network
import com.fardc.sigint.core.BlackBox
import com.fardc.sigint.security.LogicBomb

class NetworkEngine(private val logger: BlackBox, private val bomb: LogicBomb) {
    fun setInterfaceMode(iface: String, mode: String) = println("[📡] NET : $iface en mode $mode")
}
