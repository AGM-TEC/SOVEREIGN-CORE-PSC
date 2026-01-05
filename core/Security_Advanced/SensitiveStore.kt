package com.fardc.sigint.core.security

class SensitiveStore {
    private var encryptionKey: String? = "FARDC-ALPHA-992-SECURE"

    fun getKey(): String? {
        return encryptionKey
    }

    // Fonction de brûlage instantané
    fun burnKeys() {
        encryptionKey = null
        java.lang.System.gc() // Force le ramasse-miettes pour effacer la trace en RAM
        println("[🔥] SENSITIVE STORE: Clés de déchiffrement détruites.")
    }
}
