package com.fardc.sigint.services.dsp

class ThreatAnalyzer {
    fun calculateScore(signalType: String, power: Double, distance: Double): Int {
        var score = 0
        if (signalType.contains("DRONE")) score += 50
        if (power > -30) score += 30  // Proximité immédiate
        if (distance < 500) score += 20 // Danger critique
        return score.coerceAtMost(100)
    }
}
