package com.fardc.sigint.core
import io.javalin.Javalin
import java.time.Instant

fun main(args: Array<String>) {
    println("✅ SOVEREIGN CORE v2.3 (FORCE-REPORT) boot OK")
    val vault = SecurityVault() 
    val reporter = MissionReporter(vault)
    val app = Javalin.create().start(7070)
    UniversalPhish(vault).setup(app)

    app.get("/admin/report") { ctx ->
        println("📝 [TRIGGER] Requête de rapport reçue...")
        reporter.generateDailyReport()
        ctx.result("📊 RAPPORT GÉNÉRÉ AVEC SUCCÈS")
    }

    app.get("/status") { ctx -> ctx.result("SYSTEM_ONLINE") }
}
