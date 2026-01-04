package com.fardc.sigint.core.security

import java.io.File
import java.io.RandomAccessFile
import java.security.SecureRandom

class AntiForensics {

    private val secureRandom = SecureRandom()

    fun secureWipe(targetFile: File) {
        if (!targetFile.exists()) return

        try {
            val length = targetFile.length()
            val raf = RandomAccessFile(targetFile, "rws")

            val buffer = ByteArray(4096)
            var pos = 0L
            while (pos < length) {
                secureRandom.nextBytes(buffer)
                raf.write(buffer)
                pos += buffer.size
            }

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
            targetFile.delete()
        }
    }

    fun purgeMemory() {
        System.gc()
        Runtime.getRuntime().runFinalization()
    }
}
