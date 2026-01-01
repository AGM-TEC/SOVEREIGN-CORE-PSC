cat << 'EOF' > src/main/kotlin/com/fardc/sigint/core/Main.kt
package com.fardc.sigint.core

import com.fardc.sigint.services.dsp.SignalClassifier 
import com.fardc.sigint.core.sync.MeshSyncEngine
import io.javalin.Javalin
import io.javalin.http.staticfiles.Location
import java.io.File
import java.time.Instant

fun main(args: Array<String>) {
    println("✅ SOVEREIGN CORE v2.4.0 (ULTIMATE-INTEGRATION) boot OK")

    val app = Javalin.create { config ->
        // --- EXPOSITION DES RÉPERTOIRES TACTIQUES (STATIQUES) ---
        // Permet d'accéder aux interfaces et ressources de chaque module
        config.staticFiles.add("./ui", Location.EXTERNAL)
        config.staticFiles.add("./Sovereign-Offensive", Location.EXTERNAL)
        config.staticFiles.add("./bft", Location.EXTERNAL)
        config.staticFiles.add("./sigint", Location.EXTERNAL)
        config.staticFiles.add("./infra", Location.EXTERNAL)
        config.staticFiles.add("./services", Location.EXTERNAL)
        config.staticFiles.add("./data", Location.EXTERNAL)
        config.staticFiles.add("./reports", Location.EXTERNAL)
        config.staticFiles.add("./src/main/kotlin/com/fardc/sigint/core", Location.EXTERNAL) // Liaison Core

        config.showJavalinBanner = false
    }.start(7070)

    // Redirection racine vers l'interface principale
    app.get("/") { ctx -> ctx.redirect("/tactical/index.html") }

    // --- API DE GESTION DU CYCLE D'INTELLIGENCE ---

    // Rapport d'état global (Fusion des dossiers)
    app.get("/api/system/full-status") { ctx ->
        ctx.json(mapOf(
            "timestamp" to Instant.now().toString(),
            "modules" to listOf("OFFENSIVE", "BFT", "SIGINT", "INFRA", "SERVICES"),
            "storage" to mapOf(
                "data_samples" to (File("./data").listFiles()?.size ?: 0),
                "active_reports" to (File("./reports").listFiles()?.size ?: 0)
            ),
            "core_engine" to "VERIFIED"
        ))
    }

    // Listing des rapports pour le Général
    app.get("/api/reports") { ctx ->
        val files = File("./reports").listFiles()?.map { it.name } ?: listOf()
        ctx.json(files)
    }

    // --- INITIALISATION DES COMPOSANTS CORE ---
    val vault = SecurityVault()
    val combatHandler = CombatModeHandler()
    val meshEngine = MeshSyncEngine(vault, combatHandler)
    val aiEngine = SignalClassifier(vault, combatHandler)

    // Setup des routes spécifiques aux services
    meshEngine.setup(app)
    combatHandler.setupRoutes(app)
    
    // Endpoint IA pour la classification de signaux
    app.post("/api/ai/classify") { ctx ->
        ctx.json(aiEngine.processInference(ctx.bodyAsBytes()))
    }
}
EOF
