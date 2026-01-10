package com.fardc.sigint.core.intelligence
import com.fardc.sigint.core.BlackBox
class JointStrikeDesignator(private val logger: BlackBox) {
    fun execute(targetId: String) = println("[🎯] DESIGNATOR : Cible $targetId verrouillée. Mix d'effets Terre/Air/Cyber prêt.")
}
