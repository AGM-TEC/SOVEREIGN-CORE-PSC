package com.fardc.sigint.core

import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

/**
 * SECURITY VAULT - Protection Souveraine
 * Chiffre les signaux interceptés avant stockage
 */
class SecurityVault {
    private val key = SecretKeySpec("SOUVERAIN_FARDC_".toByteArray(), "AES")

    fun encryptData(data: String): String {
        return try {
            val cipher = Cipher.getInstance("AES")
            cipher.init(Cipher.ENCRYPT_MODE, key)
            val encrypted = cipher.doFinal(data.toByteArray())
            Base64.getEncoder().encodeToString(encrypted)
        } catch (e: Exception) {
            "ERROR_ENCRYPTION_FAILED"
        }
    }
}
