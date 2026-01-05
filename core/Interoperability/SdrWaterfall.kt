package com.fardc.sigint.core.interop

class SdrWaterfall {
    private val chars = listOf(" ", ".", ":", "-", "=", "+", "*", "#", "%", "@")

    fun renderLine(powerLevels: List<Double>) {
        val line = powerLevels.joinToString("") { p ->
            val index = ((p + 100) / 10).coerceIn(0.0, 9.0).toInt()
            chars[index]
        }
        println("[📡] |$line|")
    }
}
