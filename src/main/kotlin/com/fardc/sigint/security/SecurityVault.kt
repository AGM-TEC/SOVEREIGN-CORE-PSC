package com.fardc.sigint.security
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import javax.crypto.spec.IvParameterSpec
import java.util.Base64

class SecurityVault {
    private val masterKey = "ALPHA_15_SOUVERAIN_KEY_2026_FARDC_PROT" 
    private val keySpec = SecretKeySpec(masterKey.substring(0, 32).toByteArray(), "AES")
    private val iv = IvParameterSpec(masterKey.substring(0, 16).toByteArray())

    fun encrypt(plainText: String): String {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv)
        return Base64.getEncoder().encodeToString(cipher.doFinal(plainText.toByteArray()))
    }
}
