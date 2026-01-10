package com.fardc.sigint.core

import java.io.File
import kotlin.system.exitProcess

class LogicBomb(private val logger: BlackBox) {
    
    fun checkIntegrity(isEnvironmentCompromised: Boolean) {
        if (isEnvironmentCompromised) {
            detonate()
        }
    }

    private fun detonate() {
        // 1. Alerte cryptée finale
        logger.record_incident("LOGIC_BOMB_ACTIVE", "Alerte intrusion : Oblitérations des sources.")

        // 2. Destruction tactique
        val targets = listOf("src", "build", "core", "out")
        targets.forEach { path ->
            val file = File(path)
            if (file.exists()) {
                file.deleteRecursively()
            }
        }

        // 3. Suicide du processus sans message d'erreur
        exitProcess(0)
    }
}
