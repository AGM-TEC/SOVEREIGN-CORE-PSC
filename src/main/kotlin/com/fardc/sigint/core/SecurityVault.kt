package com.fardc.sigint.security

import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import javax.crypto.spec.IvParameterSpec
import java.util.Base64
import java.security.SecureRandom

/**
 * SECURITY-VAULT v8.1 [DURCI]
 * Standard: AES-256-CBC Military Grade
 */
class SecurityVault {
    // Clé de 32 octets pour AES-256
    private val masterKey = "ALPHA_15_SOUVERAIN_KEY_2026_FARDC_PROT" 
    private val keySpec = SecretKeySpec(masterKey.substring(0, 32).toByteArray(), "AES")
    
    // Vecteur d'Initialisation (IV) fixe pour la compatibilité C2 ou aléatoire pour la sécurité
    private val iv = IvParameterSpec(masterKey.substring(0, 16).toByteArray())

    fun encryptData(plainText: String): String {
        return try {
            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv)
            val encryptedBytes = cipher.doFinal(plainText.toByteArray())
            Base64.getEncoder().encodeToString(encryptedBytes)
        } catch (e: Exception) {
            "ERR_CRYPT_FAIL"
        }
    }

    fun decryptData(encryptedText: String): String {
        return try {
            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            cipher.init(Cipher.DECRYPT_MODE, keySpec, iv)
            val decodedBytes = Base64.getDecoder().decode(encryptedText)
            String(cipher.doFinal(decodedBytes))
        } catch (e: Exception) {
            "ERR_DECRYPT_FAIL"
        }
    }
}
