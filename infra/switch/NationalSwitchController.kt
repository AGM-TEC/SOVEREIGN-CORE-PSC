package infra.switch

import sigint.audit.LogManager
import sigint.core.Gatekeeper
import java.util.concurrent.ConcurrentHashMap

/**
 * SRC - NationalSwitchController (GRADE 0 - Thread-Safe)
 * Contrôleur de la Chambre de Compensation Nationale.
 */
class NationalSwitchController(private val gatekeeper: Gatekeeper) {

    // Pivot Technique : Structure Thread-Safe pour éviter les crashs de concurrence (Race Conditions)
    // Limite théorique fixée pour prévenir les fuites de mémoire (OOM) sur terminal mobile.
    private val blacklistedAccounts = ConcurrentHashMap.newKeySet<String>()
    private val MAX_BLACKLIST_SIZE = 500_000 

    /**
     * Point d'entrée asynchrone pour le filtrage ISO 8583.
     */
    fun onTransactionRequest(isoMessage: ISOMessage, authSignature: ByteArray): ISOMessage {
        if (!gatekeeper.verifyCommand(authSignature)) {
            LogManager.warn("SWITCH_AUTH_FAIL: Rejet de commande non signée.")
            return isoMessage 
        }

        val targetAccount = isoMessage.getField(102)

        // Recherche en temps constant O(1)
        if (blacklistedAccounts.contains(targetAccount)) {
            LogManager.info("SWITCH_BLOCK: Interception du flux vers $targetAccount.")
            
            return isoMessage.apply {
                setField(39, "57") // 57 = Transaction non permise (Standard ISO 8583)
                setField(44, "STATE_SEIZURE_ORDER")
            }
        }

        return isoMessage
    }

    /**
     * Mise à jour de la matrice de ciblage.
     */
    fun updateBlacklist(newTargets: List<String>) {
        // Disjoncteur mémoire : Purge si la limite physique est atteinte
        if (blacklistedAccounts.size + newTargets.size > MAX_BLACKLIST_SIZE) {
            LogManager.warn("SWITCH_MEM_WARN: Purge thermique de la liste noire.")
            blacklistedAccounts.clear()
        }
        
        blacklistedAccounts.addAll(newTargets)
        LogManager.info("SWITCH_SYNC: Matrice mise à jour (${blacklistedAccounts.size} cibles actives).")
    }
}

data class ISOMessage(
    val fields: MutableMap<Int, String> = mutableMapOf()
) {
    fun getField(id: Int): String = fields[id] ?: ""
    fun setField(id: Int, value: String) { fields[id] = value }
}
