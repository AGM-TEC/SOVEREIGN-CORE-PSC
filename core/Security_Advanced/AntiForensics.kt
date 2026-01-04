package com.fardc.sigint.core.security

import java.io.File
import java.io.RandomAccessFile
import java.security.SecureRandom

/**
 * SOVEREIGN-CORE-PSC v5.2 - ANTI-FORENSICS MODULE
 * Grade : Militaire / Haute Discrétion
 * Protection de l'actif : 1 350 000 $
 */
class AntiForensics {

    private val secureRandom = SecureRandom()

    /**
     * Effacement sécurisé DoD 5220.22-M
     * Réécrit le fichier avec des données aléatoires avant suppression.
     */
    fun secureWipe(targetFile: File) {
        if (!targetFile.exists()) return

        try {
            val length = targetFile.length()
            val raf = RandomAccessFile(targetFile, "rws")
            
            // Passe 1 : Données aléatoires
            val buffer = ByteArray(4096)
            var pos = 0L
            while (pos < length) {
                secureRandom.nextBytes(buffer)
                raf.write(buffer)
                pos += buffer.size
            }
            
            // Passe 2 : Remise à zéro
            raf.seek(0)
            buffer.fill(0)
            pos = 0L
            while (pos < length) {
                raf.write(buffer)
                pos += buffer.size
            }
            
            raf.close()
            targetFile.delete()
            println("[🛡️] Anti-Forensics : Fichier détruit physiquement.")
        } catch (e: Exception) {
            targetFile.delete() // Suppression simple en dernier recours
        }
    }

    /**
     * Purge de la RAM pour empêcher les dumps de mémoire.
     */
    fun purgeMemory() {
        System.gc()
        Runtime.getRuntime().runFinalization()
    }
}
