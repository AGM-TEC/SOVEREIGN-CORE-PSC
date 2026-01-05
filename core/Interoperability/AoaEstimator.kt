package com.fardc.sigint.core.interop

import kotlin.math.atan2

class AoaEstimator {
    fun calculateBearing(iqData: ByteArray): Double {
        // Simulation de calcul de phase pour trouver la direction de l'émetteur
        val angle = atan2(1.0, 1.0) * (180 / Math.PI)
        println("[📍] SIGINT-GEO : Signal détecté à un angle de ${angle}°")
        return angle
    }
}
