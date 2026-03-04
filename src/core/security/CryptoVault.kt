package core.security

import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.util.Base64

object CryptoVault {
    // Algorithme de hachage de grade industriel
    private const val HASH_ALGORITHM = "SHA-256"
    // Taille du sel dynamique (256 bits)
    private const val SALT_LENGTH_BYTES = 32

    fun signTacticalOrder(orderId: String, action: String): String {
        try {
            // 1. Extraction de l'entropie matérielle (Point de blocage potentiel si le noyau est à sec)
            val dynamicSalt = SecurityVault.generateHardwareEntropy(SALT_LENGTH_BYTES)
            val saltBase64 = Base64.getEncoder().encodeToString(dynamicSalt)
            
            // 2. Concaténation des vecteurs de données
            val timestamp = System.currentTimeMillis()
            val rawPayload = "$orderId|$action|$timestamp|$saltBase64"
            
            // 3. Calcul de l'empreinte cryptographique
            val digest = MessageDigest.getInstance(HASH_ALGORITHM)
            val hashBytes = digest.digest(rawPayload.toByteArray(StandardCharsets.UTF_8))
            val signature = Base64.getEncoder().encodeToString(hashBytes)
            
            println("[CRYPTO] Ordre $orderId scellé. Empreinte: $signature")
            return "$signature|$saltBase64|$timestamp"
            
        } catch (e: Exception) {
            // Mécanique de rupture : Si la cryptographie échoue, l'ordre est détruit
            throw SecurityException("ÉCHEC DU SCELLAGE CRYPTO : L'ordre $orderId est invalidé. Cause: ${e.message}")
        }
    }

    fun verifyOrderIntegrity(orderId: String, action: String, payload: String): Boolean {
        // La charge utile (payload) contient la signature, le sel et l'horodatage séparés par un pipe (|)
        val parts = payload.split("|")
        if (parts.size != 3) return false
        
        val signature = parts[0]
        val saltBase64 = parts[1]
        val timestamp = parts[2]
        
        // Règle de péremption : Un ordre de plus de 5000ms est rejeté (Anti-Replay)
        if (System.currentTimeMillis() - timestamp.toLong() > 5000) {
            println("[CRYPTO] REJET : Ordre tactique périmé.")
            return false
        }
        
        val rawPayload = "$orderId|$action|$timestamp|$saltBase64"
        val digest = MessageDigest.getInstance(HASH_ALGORITHM)
        val hashBytes = digest.digest(rawPayload.toByteArray(StandardCharsets.UTF_8))
        val computedSignature = Base64.getEncoder().encodeToString(hashBytes)
        
        return computedSignature == signature
    }
}
