package com.fardc.sigint.core.security
import java.io.File

class GeofenceManager(val polyFile: String) {
    private var activePolygon: List<Pair<Double, Double>>? = null
    fun loadFence() {
        val file = File(polyFile)
        if (file.exists()) {
            activePolygon = file.readLines().mapNotNull { line ->
                val parts = line.split(",")
                if (parts.size == 2) parts[0].toDouble() to parts[1].toDouble() else null
            }
        }
    }
}
