package com.fardc.sigint.core

import com.fardc.sigint.services.dsp.SignalClassifier 
import com.fardc.sigint.core.sync.MeshSyncEngine
import com.fardc.sigint.core.network.SdrBridge
import com.fardc.sigint.core.network.SignalStreamer
import com.fardc.sigint.core.hardware.SovereignHardware
import io.javalin.Javalin
import io.javalin.http.staticfiles.Location
import java.io.File
import java.time.Instant
import kotlin.concurrent.thread

fun main(args: Array<String>) {
    println("✅ SOVEREIGN CORE v4.0 (BATTLE-LINK) boot OK")

    // --- 0. INITIALISATION DES COMPOSANTS RADIO & STREAMING ---
    val sdrBridge = SdrBridge("127.0.0.1", 14423)
    val sdrHardware = try { SovereignHardware() } catch(e: Exception) { null }
    val signalStreamer = SignalStreamer()

    val app = Javalin.create { config ->
        // --- 1. LIAISONS INTERFACES & ASSETS ---
        config.staticFiles.add("./ui", Location.EXTERNAL)
        config.staticFiles.add("./Sovereign-Offensive", Location.EXTERNAL)
        config.staticFiles.add("./bft", Location.EXTERNAL)
        config.staticFiles.add("./sigint", Location.EXTERNAL)

        // --- 2. LIAISONS DONNÉES & RAPPORTS ---
        config.staticFiles.add("./data", Location.EXTERNAL)
        config.staticFiles.add("./reports", Location.EXTERNAL)

        // --- 3. LIAISONS INFRASTRUCTURE & SERVICES ---
        config.staticFiles.add("./infra", Location.EXTERNAL)
        config.staticFiles.add("./services", Location.EXTERNAL)
        config.staticFiles.add("./scripts", Location.EXTERNAL)
        config.staticFiles.add("./docs", Location.EXTERNAL)
        config.staticFiles.add("./specs", Location.EXTERNAL)

        config.showJavalinBanner = false
    }.start(7070)

    // --- 4. ACTIVATION DU FLUX HUD (WEBSOCKET) ---
    signalStreamer.setup(app)

    // --- 5. ROUTAGE TACTIQUE & API C2 ---
    app.get("/") { ctx -> ctx.redirect("/tactical/index.html") }

    app.get("/api/system/summary") { ctx ->
        ctx.json(mapOf(
            "status" to "BATTLE_READY",
            "uptime" to Instant.now().toString(),
            "active_modules" to listOf("SIGINT", "BFT", "OFFENSIVE", "MESH", "SDR_CORE"),
            "hardware_native" to (sdrHardware != null),
            "network_bridge" to sdrBridge.connect(),
            "security_level" to "AES-256-ENFORCED"
        ))
    }

    app.post("/api/offensive/deploy") { ctx ->
        val payload = ctx.queryParam("payload") ?: "GENERIC_JAMMER"
        val freq = ctx.queryParam("freq")?.toLong() ?: 91800000L
        println("🔥 [WARFARE] Déploiement $payload sur ${freq/1000000.0} MHz")
        
        sdrHardware?.setFrequency(freq) // Pilotage physique direct
        ctx.json(mapOf("action" to "DEPLOYED", "target_freq" to freq))
    }

    // --- 6. INITIALISATION CORE SERVICES ---
    val vault = SecurityVault()
    val combatHandler = CombatModeHandler()
    val meshEngine = MeshSyncEngine(vault, combatHandler)
    val aiEngine = SignalClassifier(vault, combatHandler)

    meshEngine.setup(app)
    combatHandler.setupRoutes(app)

    // --- 7. BOUCLE DE CAPTURE ET DIFFUSION HUD ---
    thread(start = true, isDaemon = true) {
        println("📡 [SCANNER] Démarrage du flux de données SDR...")
        while (true) {
            val rawData = sdrBridge.receiveSignal()
            if (rawData.isNotEmpty()) {
                val dbm = rawData.map { it.toInt() }.average()
                // Envoi des données vers l'oscilloscope JS
                signalStreamer.broadcastSpectrumData("""{"power": $dbm, "freq": 91800000}""")
            }
            Thread.sleep(100)
        }
    }
}
