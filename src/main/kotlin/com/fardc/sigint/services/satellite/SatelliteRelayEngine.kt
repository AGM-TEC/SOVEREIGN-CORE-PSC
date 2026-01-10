package com.fardc.sigint.services.satellite
import com.fardc.sigint.core.BlackBox
class SatelliteRelayEngine(private val logger: BlackBox) {
    fun establishUplink(const: String) = println("[🛰️] SAT-LINK : Liaison établie avec la constellation $const.")
}
