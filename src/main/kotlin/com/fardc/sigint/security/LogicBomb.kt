package com.fardc.sigint.security

import com.fardc.sigint.core.BlackBox
import java.io.File
import java.io.FileOutputStream
import java.security.SecureRandom

/**
 * LOGIC-BOMB v8.1 [NIVEAU ALPHA]
 * Standard: Military-Grade Anti-Forensic Wipe
 */
class LogicBomb(private val logger: BlackBox) {
    private var failedAttempts = 0
    private val MAX_ATTEMPTS = 3
    private val random = SecureRandom()

    fun triggerFailedAttempt() {
        failedAttempts++
        logger.recordIncident("ALERTE_SÉCURITÉ", "Tentative d'accès non autorisée ($failedAttempts/$MAX_ATTEMPTS)")

        if (failedAttempts >= MAX_ATTEMPTS) {
            detonate()
        }
    }

    private fun detonate() {
        println("\n[☢️] PROTOCOLE TERRE BRÛLÉE ACTIVÉ")
        
        try {
            val sourceDir = File("src")
            if (sourceDir.exists()) {
                wipeDirectory(sourceDir)
                sourceDir.deleteRecursively()
            }
            
            // Création d'un flag de verrouillage matériel
            File(".DEADLOCK").writeText("SYSTEM_COMPROMISED_${System.currentTimeMillis()}")
            
            println("[🔥] OBLITÉRATION TERMINÉE. SYSTÈME IRRÉCUPÉRABLE.")
        } catch (e: Exception) {
            // Failsafe : Arrêt immédiat si l'effacement échoue
        } finally {
            System.exit(1)
        }
    }

    private fun wipeFile(file: File) {
        if (file.isFile) {
            val length = file.length()
            val out = FileOutputStream(file)
            // Surcharge avec des données aléatoires pour briser la récupération magnétique/flash
            val junk = ByteArray(4096)
            var written = 0L
            while (written < length) {
                random.nextBytes(junk)
                out.write(junk)
                written += junk.size
            }
            out.close()
        }
    }

    private fun wipeDirectory(dir: File) {
        dir.listFiles()?.forEach { file ->
            if (file.isDirectory) wipeDirectory(file) else wipeFile(file)
        }
    }
}
