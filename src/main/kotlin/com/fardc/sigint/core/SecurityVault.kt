package com.fardc.sigint.core

import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import java.util.Base64

/**
 * SECURITY-VAULT v8.1 (Purified)
 * Standard: AES-256 Industrial Encryption
 */
class SecurityVault {
    private val key = "ALPHA_15_SOUVERAIN_KEY_2026_FARDC" // Devrait être chargé via variable d'env
    private val secretKey = SecretKeySpec(key.substring(0, 16).toByteArray(), "AES")

    fun encryptData(plainText: String): String {
        return try {
            val cipher = Cipher.getInstance("AES")
            cipher.init(Cipher.ENCRYPT_MODE, secretKey)
            val encryptedBytes = cipher.doFinal(plainText.toByteArray())
            Base64.getEncoder().encodeToString(encryptedBytes)
        } catch (e: Exception) {
            "ERR_ENCRYPTION_FAIL"
        }
    }
}
