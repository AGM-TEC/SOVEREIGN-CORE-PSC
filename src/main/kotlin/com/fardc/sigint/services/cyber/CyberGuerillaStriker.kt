package com.fardc.sigint.services.cyber
import com.fardc.sigint.core.BlackBox
class CyberGuerillaStriker(private val logger: BlackBox) {
    fun strike(target: String) = println("[⚡] CYBER-STRIKER : Opération asymétrique sur $target réussie.")
}
