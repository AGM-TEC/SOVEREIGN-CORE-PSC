package com.fardc.sigint.core

fun main() {
    println("--------------------------------------------")
    println("🛡️ SOVEREIGN-CORE-PSC | SYSTÈME UNIFIÉ V2")
    println("📡 Statut : Opérationnel & Sécurisé")
    println("--------------------------------------------")

    val vault = SecurityVault()
    val scanner = OffensiveScanner()
    val cyber = FinCapInterception()
    
    // Initialisation sécurisée
    println("[SYSTEM] Chiffrement des identifiants de session...")
    val sessionKey = vault.encryptData("SESSION_ACTIVE_${System.currentTimeMillis()}")
    println("[SYSTEM] Token de session : $sessionKey")

    // Lancement de l'interface de commande
    val cmd = CommandInterface(scanner, cyber)
    cmd.start()
}
