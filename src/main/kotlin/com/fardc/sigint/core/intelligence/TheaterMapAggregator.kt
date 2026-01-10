package com.fardc.sigint.core.intelligence
import com.fardc.sigint.core.BlackBox
import java.util.concurrent.ConcurrentHashMap

class TheaterMapAggregator(private val logger: BlackBox) {
    private val tacticalSituation = ConcurrentHashMap<String, String>()
    fun updateSituation(id: String, type: String) {
        println("[🗺️] COP-UPDATE : $type détecté [$id]")
    }
}
