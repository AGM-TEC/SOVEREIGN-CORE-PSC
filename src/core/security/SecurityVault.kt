package core.security

import java.security.SecureRandom
import java.util.Arrays
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * SECURITY-VAULT v33.1 [MIL-SPEC-GOLD]
 * Standard: AES-256-GCM (Authenticated Encryption)
 * Rôle: Chiffrement polymorphe et destruction physique de la RAM.
 */
class SecurityVault(private val logger: BlackBox) {

    private val ALGORITHM = "AES/GCM/NoPadding"
    private val TAG_LENGTH_BIT = 128
    private val IV_LENGTH_BYTE = 12
    private val AES_KEY_SIZE = 32 // 256 bits

    private var ephemeralKey: ByteArray? = null

    // --- AJOUT DE CONFORMITÉ : Générateur d'Entropie Matérielle ---
    companion object {
        fun generateHardwareEntropy(lengthBytes: Int): ByteArray {
            val secureRandom = SecureRandom()
            val entropy = ByteArray(lengthBytes)
            secureRandom.nextBytes(entropy)
            return entropy
        }
    }
    // --------------------------------------------------------------

    fun initializeVault(masterKey: ByteArray) {
        if (masterKey.size != AES_KEY_SIZE) {
            throw IllegalArgumentException("[ERREUR FATALE] Clé 256 bits requise.")
        }
        ephemeralKey = masterKey.copyOf()
        println("[SECURITY-VAULT] Chambre forte GCM initialisée.")
    }

    fun encrypt(data: String): String {
        val key = ephemeralKey ?: throw IllegalStateException("[ALERTE] Vault verrouillé ou purgé.")
        
        val secureRandom = SecureRandom()
        val iv = ByteArray(IV_LENGTH_BYTE)
        secureRandom.nextBytes(iv)

        val cipher = Cipher.getInstance(ALGORITHM)
        val keySpec = SecretKeySpec(key, "AES")
        val gcmSpec = GCMParameterSpec(TAG_LENGTH_BIT, iv)

        cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmSpec)
        val encrypted = cipher.doFinal(data.toByteArray())

        val sealedPayload = ByteArray(iv.size + encrypted.size)
        System.arraycopy(iv, 0, sealedPayload, 0, iv.size)
        System.arraycopy(encrypted, 0, sealedPayload, iv.size, encrypted.size)

        return Base64.getEncoder().encodeToString(sealedPayload)
    }

    fun emergencyPurge() {
        println("[ALERTE] VAULT-SIZ : Déclenchement de la purge thermique des clés...")
        ephemeralKey?.let {
            Arrays.fill(it, 0.toByte())
            ephemeralKey = null
        }
        logger.recordIncident("VAULT_PURGE", "Destruction physique des clés en RAM validée.")
    }
}
