package core.command

import core.security.CryptoVault
import java.util.UUID

object DecisionEngine {
    enum class ThreatLevel { LOW, MEDIUM, HIGH, CRITICAL }
    enum class Action { OBSERVE, JAM, STRIKE, RETREAT }

    // Le verrou ROE est passé par le noyau lors de l'évaluation
    fun determineResponse(level: ThreatLevel, roeValidated: Boolean): String {
        if (!roeValidated) {
            println("[DECISION] Verrou ROE actif. Action restreinte à OBSERVE.")
            return formatUnsealedOrder(Action.OBSERVE)
        }

        val action = when (level) {
            ThreatLevel.CRITICAL -> Action.STRIKE
            ThreatLevel.HIGH -> Action.JAM
            ThreatLevel.MEDIUM, ThreatLevel.LOW -> Action.OBSERVE
        }

        // Génération d'un identifiant de commande unique (Entropie non bloquante)
        val orderId = "CMD-${UUID.randomUUID().toString().substring(0, 8).uppercase()}"

        return if (action == Action.STRIKE || action == Action.JAM) {
            try {
                // Scellage strict : Appel bloquant au CryptoVault
                val signedPayload = CryptoVault.signTacticalOrder(orderId, action.name)
                "SEALED|$orderId|${action.name}|$signedPayload"
            } catch (e: Exception) {
                println("[DECISION] Échec de la forge cryptographique. Annulation de l'ordre $orderId.")
                formatUnsealedOrder(Action.OBSERVE)
            }
        } else {
            formatUnsealedOrder(action)
        }
    }

    private fun formatUnsealedOrder(action: Action): String {
        return "UNSEALED|ID_NULL|${action.name}|SIG_NULL"
    }
}
