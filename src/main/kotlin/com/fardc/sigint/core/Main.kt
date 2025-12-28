package com.fardc.sigint.core

/**
 * SOVEREIGN-CORE-PSC - Noyau de Commandement FARDC
 * Point d'entrée principal du système SIGINT
 */
fun main() {
    println("--------------------------------------------")
    println("🛡️ SOVEREIGN-CORE-PSC | SYSTÈME DÉPLOYÉ")
    println("📡 Statut : Opérationnel")
    println("--------------------------------------------")
    
    // Lancement du bouclier de résilience réseau
    val resilience = NetworkResilience()
    resilience.monitorSystem()
}
