package com.fardc.sigint.core

import java.util.concurrent.ConcurrentHashMap

/**
 * DASHBOARD BRUTE-FORCE PSC
 * Monitoring des tentatives d'intrusion interceptées
 */
class BruteForceDashboard {
    private val attackMap = ConcurrentHashMap<String, Int>()

    fun recordAttempt(ip: String) {
        val attempts = attackMap.getOrDefault(ip, 0) + 1
        attackMap[ip] = attempts
        
        println("\n📊 [DASHBOARD] Mise à jour des renseignements :")
        println("📡 Cible : $ip | Tentatives interceptées : $attempts")
        
        if (attempts > 10) {
            println("🚨 [ALERTE] Activité suspecte haute intensité détectée !")
        }
    }
}
