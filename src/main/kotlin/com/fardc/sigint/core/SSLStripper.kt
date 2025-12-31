package com.fardc.sigint.core

class SSLStripper {
    fun downgradeConnection(url: String): String {
        println("🔓 [SSL-STRIP] Dégradation de la cible...")
        return url.replace("https://", "http://")
    }
}
