package com.fardc.sigint.core.security

import java.io.File
import java.io.RandomAccessFile

class PanicWipeManager {
    private val criticalFiles = listOf("mpesa_vault.db", "mpesa_vault.db.sealed", "rdc_rebel_nets.json")

    fun executeTotalWipe() {
        println("[🚨] PANIC WIPE: Déclenchement de la destruction irréversible...")
        
        criticalFiles.forEach { fileName ->
            val file = File(fileName)
            if (file.exists()) {
                secureErase(file)
            }
        }
        println("[💀] SYSTEM CLEAN: Toutes les preuves ont été pulvérisées.")
    }

    private fun secureErase(file: File) {
        // Remplit le fichier de zéros avant de le supprimer pour effacer la trace magnétique
        val raf = RandomAccessFile(file, "rws")
        raf.setLength(file.length())
        val zeros = ByteArray(1024)
        var i = 0L
        while (i < file.length()) {
            raf.write(zeros)
            i += 1024
        }
        raf.close()
        file.delete()
        println("[🗑️] ERASED: ${file.name} (Zero-Filled)")
    }
}
