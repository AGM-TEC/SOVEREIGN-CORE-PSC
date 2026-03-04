package core.state

// Définition stricte des postures doctrinales
enum class CombatState {
    STANDBY,    // Veille : Économie d'énergie, écoute réseau.
    STEALTH,    // Furtif : SIGINT passif, zéro émission RF.
    OFFENSIVE,  // Actif : Interception et analyse.
    LOCKDOWN    // Compromission : Isolation réseau.
}

class CombatModeHandler {
    private var currentState: CombatState = CombatState.STANDBY
    private val lock = Any()

    // Arbitrage des États : Barrière entre l'IHM et le Core
    fun transitionTo(newState: CombatState, authorizationKey: String): Boolean {
        synchronized(lock) {
            // Validation de l'Ordre
            if (!verifySignature(authorizationKey)) {
                println("[ALERTE] Tentative de transition non autorisée vers $newState")
                return false
            }

            // Protection Anti-Flapping
            if (currentState == newState) return true

            // Règle d'Engagement Stricte
            if (currentState == CombatState.LOCKDOWN) {
                println("[ERREUR FATALE] Système verrouillé en mode survie. Transition impossible.")
                return false
            }

            println("[TRANSITION SCADA] État précédent : $currentState -> Nouvel état : $newState")
            currentState = newState
            
            return true
        }
    }

    private fun verifySignature(key: String): Boolean {
        return key.startsWith("AUTH-SOUVERAIN")
    }
    
    fun getCurrentState(): CombatState = currentState
}
