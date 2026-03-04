package modules.finint

import core.security.BlackBox
import java.util.regex.Pattern

/**
 * PAGO-MOVIL INTERCEPTOR v33.1 [VNZ-SPECIFIC]
 * Rôle: Filtrage et extraction algorithmique des métadonnées financières (Démonstrateur Tactique).
 */
class PagoMovilInterceptor(private val logger: BlackBox) {

    // Expressions régulières (Regex) calibrées pour les formats interbancaires vénézuéliens
    // Cible la devise (VES/Bs.) et le format standard des numéros cellulaires locaux
    private val amountRegex = Pattern.compile("(?:Bs\\.?|VES)\\s*([0-9]{1,3}(?:\\.[0-9]{3})*(?:,[0-9]{2})?)")
    private val phoneRegex = Pattern.compile("04(?:14|24|16|26|12)[0-9]{7}")
    
    fun analyzePayload(payload: String): Boolean {
        println("[FinINT] Analyse du paquet de données intercepté...")
        
        val amountMatcher = amountRegex.matcher(payload)
        val phoneMatcher = phoneRegex.matcher(payload)

        // Corrélation stricte : un flux n'est critique que s'il contient un montant ET un identifiant
        if (amountMatcher.find() && phoneMatcher.find()) {
            val amount = amountMatcher.group(1)
            val phone = phoneMatcher.group()
            
            println("[!] VERROUILLAGE FINANCIER [!]")
            println(" -> Vecteur de transaction : Pago Móvil")
            println(" -> Identifiant Cible    : $phone")
            println(" -> Volume Transféré     : $amount VES")
            
            // Enregistrement immuable dans le système d'audit
            logger.recordIncident("FININT_INTERCEPT", "Flux capturé: $amount VES associé au $phone")
            return true
        }
        
        println("[FinINT] Rejet : Le paquet ne contient aucune donnée financière exploitable.")
        return false
    }
}
