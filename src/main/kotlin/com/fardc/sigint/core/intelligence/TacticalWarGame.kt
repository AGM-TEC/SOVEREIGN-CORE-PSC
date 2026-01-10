package com.fardc.sigint.core.intelligence

import com.fardc.sigint.core.BlackBox
import kotlin.math.sqrt

/**
 * TACTICAL-WAR-GAME v9.2 [INDUSTRIAL GRADE]
 * Standard: Lanchester Square Law & Graph Theory Analysis
 */
class TacticalWarGame(private val logger: BlackBox) {

    data class ForceStats(val troops: Double, val techFactor: Double)
    data class SimResult(val winProbability: Double, val estimatedLosses: Double, val tacticalAdvantage: String)

    fun runHighSpectrumSim(friendly: ForceStats, enemy: ForceStats): SimResult {
        println("[🛰️] SIMULATION INDUSTRIELLE : Calcul de la projection de force...")

        // Loi de Lanchester : La puissance de combat est proportionnelle au carré de l'effectif
        // multiplié par la qualité technologique
        val friendlyPower = (friendly.troops * friendly.troops) * friendly.techFactor
        val enemyPower = (enemy.troops * enemy.troops) * enemy.techFactor

        val winProb = friendlyPower / (friendlyPower + enemyPower)
        
        // Calcul de l'atrittion (pertes estimées)
        val losses = if (winProb > 0.5) {
            enemy.troops / sqrt(friendly.techFactor)
        } else {
            friendly.troops * 0.8
        }

        val analysis = when {
            winProb > 0.8 -> "DOMINATION TOTALE : Manœuvre d'enveloppement recommandée."
            winProb > 0.5 -> "VICTOIRE COÛTEUSE : Soutien logistique critique requis."
            else -> "RUPTURE : Repli stratégique et guerre asymétrique conseillés."
        }

        logger.recordIncident("WARGAME_ADVANCED", "Prob: $winProb | Attrition: $losses")
        return SimResult(winProb, losses, analysis)
    }
}
