cat << 'EOF' > src/main/kotlin/com/fardc/sigint/core/Main.kt
package com.fardc.sigint.core

import com.fardc.sigint.services.dsp.SignalClassifier 
import com.fardc.sigint.core.sync.MeshSyncEngine
import io.javalin.Javalin
import io.javalin.http.staticfiles.Location
import java.io.File
import java.time.Instant

fun main(args: Array<String>) {
    println("✅ SOVEREIGN CORE v2.4.0 (EXECUTIVE-DEPLOYMENT) boot OK")

    val app = Javalin.create { config ->
        // --- LIAISONS OPÉRATIONNELLES (VISIBLES VIA NAVIGATEUR) ---
        config.staticFiles.add("./ui", Location.EXTERNAL)
        config.staticFiles.add("./Sovereign-Offensive", Location.EXTERNAL)
        config.staticFiles.add("./bft", Location.EXTERNAL)
        config.staticFiles.add("./sigint", Location.EXTERNAL)
        config.staticFiles.add("./infra", Location.EXTERNAL)
        config.staticFiles.add("./services", Location.EXTERNAL)
        config.staticFiles.add("./data", Location.EXTERNAL)
        config.staticFiles.add("./reports", Location.EXTERNAL)
        
        // --- LIAISONS DOCUMENTATION & SPECS ---
        config.staticFiles.add("./docs", Location.EXTERNAL)
        config.staticFiles.add("./specs", Location.EXTERNAL)
        config.staticFiles.add("./scripts", Location.EXTERNAL) // Pour monitoring scripts
        
        config.showJavalinBanner = false
    }.start(7070)

    // Entrée Tactique
    app.get("/") { ctx -> ctx.redirect("/tactical/index.html") }

    // API de consultation de la documentation pour le Dashboard
    app.get("/api/docs/sitrep") { ctx ->
        val sitrep = File("docs/SITREP_GENERAL.md")
        if (sitrep.exists()) ctx.result(sitrep.readText()) else ctx.status(404)
    }

    // Moteurs Core (IA & MESH)
    val vault = SecurityVault()
    val combatHandler = CombatModeHandler()
    MeshSyncEngine(vault, combatHandler).setup(app)
    combatHandler.setupRoutes(app)
}
EOF
