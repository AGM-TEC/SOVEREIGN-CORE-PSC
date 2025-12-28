package com.fardc.sigint.core

import java.io.File

/**
 * MODULE ANTI-FORENSICS PSC
 * Nettoyage automatisé des traces opérationnelles
 */
class AntiForensics {
    fun wipeOperationalTraces() {
        println("[SECURITY] Initialisation du protocole Anti-Forensics...")
        
        val targets = listOf("manifest.txt", "compiler.options", "build/libs/sigint-core-all.jar")
        
        targets.forEach { path ->
            val file = File(path)
            if (file.exists()) {
                // Écrasement des données avant suppression
                file.writeBytes(ByteArray(file.length().toInt()) { 0 })
                if (file.delete()) {
                    println("[CLEAN] Trace éliminée : $path")
                }
            }
        }
        println("[SECURITY] Système purgé.")
    }
}
