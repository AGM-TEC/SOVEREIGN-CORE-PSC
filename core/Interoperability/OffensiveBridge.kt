package com.fardc.sigint.core.interop

import java.io.File

class OffensiveBridge {
    fun triggerZeroDay(targetIp: String) {
        println("[🚀] BRIDGE : Signal d'attaque envoyé au ZeroDayInjector pour $targetIp")
        
        // Exécution du script Python avec l'IP cible en argument
        val process = ProcessBuilder("python3", 
            "Sovereign-Offensive/vectors/infra_cloud/zero_day_manager.py", 
            "--target", targetIp, "--mode", "stealth")
            .directory(File("."))
            .start()
            
        println("[✅] BRIDGE : Injection en cours dans un thread séparé.")
    }
}
