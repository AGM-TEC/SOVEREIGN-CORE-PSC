package com.fardc.sigint.core

fun main() {
    println("🛡️ SOVEREIGN-CORE-PSC | ARSENAL COMPLET V3")
    
    val bomb = LogicBomb()
    val stealth = SignalStealth()
    val rotator = EncryptionKeyRotator()
    
    // Protection contre l'analyse (Logic-Bomb active)
    bomb.deploy(System.getenv("DEBUG_MODE") == "true")

    // Lancement du rotateur de clés de sécurité
    rotator.startRotation()
    
    // Activation de l'interface furtive
    stealth.stealthConnect("localhost", 8888)
    
    // Commandes opérationnelles
    CommandInterface(OffensiveScanner(), FinCapInterception()).start()
}
