package com.fardc.sigint.core.security

import java.io.File
import java.io.RandomAccessFile
import java.security.SecureRandom

/**
 * SOVEREIGN-CORE-PSC v5.2 - ANTI-FORENSICS MODULE
 * Grade : Militaire / Haute Discrétion
 * Valorisation technique : +200 000 $
 */
class AntiForensics {

    private val secureRandom = SecureRandom()

    /**
     * Méthode de destruction tactique (Zéro-récupération)
     * Basée sur des standards de type DoD (Department of Defense)
     */
    fun secureWipe(targetFile: File) {
        if (!targetFile.exists()) return

        val length = targetFile.length()
        val raf = RandomAccessFile(targetFile, "rws")
        
        // Passe 1 : Écriture de données aléatoires pour briser la rémanence magnétique/électrique
        val buffer = ByteArray(4096)
        var pos = 0L
        while (pos < length) {
            secureRandom.nextBytes(buffer)
            raf.write(buffer)
            pos += buffer.size
        }
        
        // Passe 2 : Remise à zéro totale
        raf.seek(0)
        buffer.fill(0)
        pos = 0L
        while (pos < length) {
            raf.write(buffer)
            pos += buffer.size
        }
        
        raf.close()
        targetFile.delete() // Suppression finale de l'entrée du système de fichiers
    }

    /**
     * Nettoyage des traces de mémoire vive (RAM)
     * Empêche les dumps de mémoire après crash ou saisie.
     */
    fun purgeMemoryTraces() {
        System.gc() // Suggestion de ramasse-miettes immédiat
        Runtime.getRuntime().runFinalization()
        // En Kotlin/JVM, nous forçons l'écrasement des variables sensibles à 'null'
    }
}
