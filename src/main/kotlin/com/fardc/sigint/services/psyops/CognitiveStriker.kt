package com.fardc.sigint.services.psyops
import com.fardc.sigint.core.BlackBox

class CognitiveStriker(private val logger: BlackBox) {
    fun injectDeception(channel: String, msg: String) {
        println("[🎭] PSY-OPS : Injection sur $channel -> $msg")
    }
}
