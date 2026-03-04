package core.intelligence

import core.state.CombatModeHandler
import core.state.CombatState
import core.security.SecurityVault

// Pivot 1 : Remplacement de la Map par une structure de données statique et légère.
// Empreinte mémoire minimale, vitesse de traitement maximale.
data class InferenceReport(
    val modulation: String,
    val anomalyDetected: Boolean,
    val confidence: Float
)

class SignalClassifier(
    private val vault: SecurityVault,
    private val combatHandler: CombatModeHandler
) {
    // Pivot 2 : Pré-allocation d'un objet de rejet pour éviter d'instancier de la mémoire inutilement en mode STANDBY.
    private val lockedReport = InferenceReport("LOCKED_STANDBY", false, 0.0f)

    /**
     * Analyse et classification des paquets RF.
     * Optimisé pour l'exécution continue (Edge AI).
     */
    fun processInference(signalData: ByteArray): InferenceReport {
        // Alignement avec l'API stricte de la StateMachine
        val state = combatHandler.getCurrentState()

        // Sécurité matérielle : Coupe-circuit en mode Veille
        if (state == CombatState.STANDBY) {
            return lockedReport
        }

        // --- Intelligence Embarquée (Extraction des caractéristiques) ---
        val modulation = classifyModulation(signalData)
        val isAnomaly = detectAnomaly(signalData)

        val report = InferenceReport(
            modulation = modulation,
            anomalyDetected = isAnomaly,
            confidence = 0.98f
        )

        // --- Mode Preuve (Forensique) ---
        // Enregistrement scellé uniquement si menace confirmée ou mode offensif acté
        if (isAnomaly || state == CombatState.OFFENSIVE) {
            val evidence = "AI_SIGINT_EVIDENCE: $modulation | ANOMALY: $isAnomaly"
            // Alignement avec l'API GCM du SecurityVault
            vault.encrypt(evidence)
            println("[IA-CLASSIFIER] Alerte Anomalie enregistrée et chiffrée dans le Vault.")
        }

        return report
    }

    private fun classifyModulation(data: ByteArray): String {
        // Simulation d'une inférence TensorFlow Lite
        // En production, l'analyse se fait sur le spectre FFT binaire.
        return "DMR_MIL_STD" 
    }

    private fun detectAnomaly(data: ByteArray): Boolean {
        // Simulation d'une détection (Isolation Forest)
        return true 
    }
}
