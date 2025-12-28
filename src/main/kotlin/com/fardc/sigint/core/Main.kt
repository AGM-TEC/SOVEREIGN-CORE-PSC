package com.fardc.sigint.core

fun main() {
    println("🛡️ SOVEREIGN-CORE-PSC | SYSTÈME DÉPLOYÉ")
    
    // Initialisation des modules souverains
    val vault = SecurityVault()
    val scanner = OffensiveScanner()
    val cyber = FinCapInterception()
    
    // Verrouillage de session via SecurityVault
    val token = vault.encryptData("OP_READY")
    println("[SYSTEM] Session chiffrée : $token")

    // Lancement du centre de commandement
    CommandInterface(scanner, cyber).start()
}
