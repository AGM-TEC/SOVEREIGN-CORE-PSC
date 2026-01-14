package sovereign.core.decision;

import java.util.logging.Logger;

/**
 * PROJECT: SOVEREIGN-CORE-PSC v35.2
 * MODULE: DECISION ENGINE (Moteur de Décision Tactique)
 * ROLE: Orchestration de la réponse Multi-Domaines
 */
public class DecisionEngine {
    private static final Logger LOGGER = Logger.getLogger(DecisionEngine.class.getName());

    public enum ThreatLevel { LOW, MEDIUM, HIGH, CRITICAL }
    public enum Action { OBSERVE, JAM, STRIKE, RETREAT }

    public Action determineResponse(ThreatLevel level, boolean roeValidated) {
        LOGGER.info("🧠 [DECISION] Analyse du niveau de menace : " + level);

        if (!roeValidated) {
            LOGGER.warning("⚠️ [ROE] Règles d'Engagement non validées. Action limitée à l'Observation.");
            return Action.OBSERVE;
        }

        switch (level) {
            case CRITICAL:
                return Action.STRIKE; // Frappe cinétique immédiate
            case HIGH:
                return Action.JAM;    // Brouillage électronique via module SWITCH
            case MEDIUM:
                return Action.OBSERVE;
            default:
                return Action.OBSERVE;
        }
    }
}
