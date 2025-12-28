package com.fardc.sigint.core

import java.util.Timer
import kotlin.concurrent.schedule

class EncryptionKeyRotator {
    private var currentKey = "INITIAL_SEC_KEY"

    fun startRotation() {
        println("[SECURITY] Rotation automatique des clés activée (5 min).")
        Timer().schedule(0, 300000) {
            currentKey = java.util.UUID.randomUUID().toString()
            println("[KEY] Nouvelle clé de chiffrement générée.")
        }
    }
}
