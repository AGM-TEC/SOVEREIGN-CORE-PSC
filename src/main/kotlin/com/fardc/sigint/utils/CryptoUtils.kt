package com.fardc.sigint.utils
import java.security.MessageDigest

object CryptoUtils {
    fun generateChecksum(data: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(data.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
}
