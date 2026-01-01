cat << 'EOF' > src/main/kotlin/com/fardc/sigint/core/Main.kt
package com.fardc.sigint.core

import com.fardc.sigint.services.dsp.SignalClassifier 
import com.fardc.sigint.core.sync.MeshSyncEngine
import io.javalin.Javalin
import io.javalin.http.staticfiles.Location
import java.io.File
import java.time.Instant

fun main(args: Array<String>) {
    println("✅ SOVEREIGN CORE v2.4.0 (FULL-SPECTRUM-COMMAND) boot OK")

    val app = Javalin.create { config ->
        // --- 1. LIAISONS INTERFACES & ASSETS ---
        config.staticFiles.add("./ui", Location.EXTERNAL)
        
        // --- 2. LIAISONS OPÉRATIONNELLES (OFFENSIF & TACTIQUE) ---
        config.staticFiles.add("./Sovereign-Offensive", Location.EXTERNAL)
        config.staticFiles.add("./bft", Location.EXTERNAL)
        config.staticFiles.add("./sigint", Location.EXTERNAL)
        
        // --- 3. LIAISONS DONNÉES & RAPPORTS ---
        config.staticFiles.add("./data", Location.EXTERNAL)
        config.staticFiles.add("./reports", Location.EXTERNAL)
        
        // --- 4. LIAISONS INFRASTRUCTURE & SERVICES ---
        config.staticFiles.add("./infra", Location.EXTERNAL)
        config.staticFiles.add("./services", Location.EXTERNAL)
        config.staticFiles.add("./scripts", Location.EXTERNAL)
        
        // --- 5. LIAISONS DOCUMENTATION & INGÉNIERIE ---
        config.staticFiles.add("./docs", Location.EXTERNAL)
        config.staticFiles.add("./specs", Location.EXTERNAL)
        config.staticFiles.add("./src/main/kotlin/com/fardc/sigint/core", Location.EXTERNAL)
        
        config.showJavalinBanner = false
    }.start(7070)

    // Redirection vers l'interface tactique par défaut
    app.get("/") { ctx -> ctx.redirect("/tactical/index.html") }

    // --- API UNIFIÉE POUR LE DASHBOARD DU GÉNÉRAL ---

    app.get("/api/system/summary") { ctx ->
        ctx.json(mapOf(
            "status" to "BATTLE_READY",
            "uptime" to Instant.now().toString(),
            "active_modules" to listOf("SIGINT", "BFT", "OFFENSIVE", "MESH"),
            "security_level" to "AES-256-ENFORCED",
            "storage" to mapOf(
                "intelligence_data" to (File("./data").listFiles()?.size ?: 0),
                "tactical_reports" to (File("./reports").listFiles()?.size ?: 0)
            )
        ))
    }

    // Endpoint spécifique pour le déploiement offensif
    app.post("/api/offensive/deploy") { ctx ->
        val payload = ctx.queryParam("payload") ?: "GENERIC_JAMMER"
        println("🔥 ALERTE : Déploiement du module offensif : $payload")
        ctx.json(mapOf("action" to "DEPLOYED", "module" to payload, "timestamp" to Instant.now().toString()))
    }

    // --- INITIALISATION DES COMPOSANTS CORE ---
    val vault = SecurityVault()
    val combatHandler = CombatModeHandler()
    val meshEngine = MeshSyncEngine(vault, combatHandler)
    val aiEngine = SignalClassifier(vault, combatHandler)

    // Activation des services Mesh et Combat
    meshEngine.setup(app)
    combatHandler.setupRoutes(app)
}
EOF
