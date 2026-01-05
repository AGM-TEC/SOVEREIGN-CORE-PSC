package com.fardc.sigint.core.interop
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.concurrent.thread

class SdrBridge {
    private var isScanning = false
    fun startRfScan(frequency: String = "900M") {
        isScanning = true
        thread(start = true) {
            println("[📻] SDR-BRIDGE : Scanning $frequency")
        }
    }
}
