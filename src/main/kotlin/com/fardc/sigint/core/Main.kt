package com.fardc.sigint.core

import io.javalin.Javalin
import java.time.Instant

/**
 * NOYAU SOUVERAIN V2.0 - RESTAURATION OFFENSIVE
 * Orchestration complète des unités SIGINT et Cyber-Action
 */
fun main(args: Array<String>) {
    println("✅ SOVEREIGN CORE boot OK @ " + Instant.now())

    // 1. Initialisation des Sentinelles
    val gatekeeper = Gatekeeper()
    val killSwitch = KillSwitch()

    // 2. Configuration du port Dashboard
    val port = args.firstOrNull()?.toIntOrNull() ?: 7070

    // 3. Instanciation des Unités de Combat
    val rotator = EncryptionKeyRotator()
    val vault = SecurityVault(rotator)
    val interceptor = FinCapInterception()
    val resilience = NetworkResilience()
    val offensiveScanner = OffensiveScanner()
    val signalJammer = SignalJammer()
    val signalStealth = SignalStealth()
    val trafficMirror = TrafficMirror()
    val sslStripper = SSLStripper() // Nouveau module de dégradation

    // 🚀 ACTIVATION DES UNITÉS PERSISTANTES (Threads de fond)
    rotator.startRotation()
    interceptor.runInterceptionMode() // Port 8889
    resilience.monitorSystem()         // Port 8888

    // 4. Démarrage du Serveur de Commandement (Javalin)
    // Ce bloc empêche le noyau de s'arrêter (Shutdown)
    val app = Javalin.create().start(port)

    println("🛡️ TUNNEL SÉCURISÉ ACTIF SUR LE PORT $port")
    println("📡 TOUS LES MODES OFFENSIFS SONT OPÉRATIONNELS")

    // 5. ENDPOINTS DE CONTRÔLE TACTIQUE

    app.get("/status") { ctx ->
        ctx.json(mapOf(
            "status" to "COMBAT_READY",
            "modules" to listOf("Jammer", "Stealth", "Mirror", "SSLStrip", "FinCap"),
            "timestamp" to Instant.now().toString()
        ))
    }

    // SATURATION : Signal-Jammer
    app.get("/jam/start") { ctx ->
        val target = ctx.queryParam("target") ?: ""
        val tPort = ctx.queryParam("port")?.toIntOrNull() ?: 80
        signalJammer.startJamming(target, tPort)
        ctx.result("🔥 [JAMMER] Attaque engagée sur $target:$tPort")
    }

    // FURTIVITÉ & SSL-STRIP : Interception dégradée
    app.get("/intercept/secure") { ctx ->
        val target = ctx.queryParam("target") ?: ""
        val downgraded = sslStripper.downgradeConnection(target)
        signalStealth.stealthConnect(downgraded, 80, "INFILTRATION_SEQUENCE")
        ctx.result("🔓 [STRIPPER] Cible dégradée : $downgraded - Canal Stealth actif.")
    }

    // URGENCE : Kill-Switch
    app.post("/emergency/halt") { ctx ->
        println("[!] HALT REÇU - NEUTRALISATION")
        killSwitch.engage()
    }
}
