package com.fardc.sigint.core

fun main(args: Array<String>) {
    val logger = BlackBox()
    val engine = StateMachine()
    val sniffer = PacketSniffer(logger)
    val fuzzer = ProtocolFuzzer(logger)
    
    // Initialisation du nouveau module d'interception (Standard Militaire)
    val proxy = ProxyInterceptor(logger, engine)
    val striker = AutonomousStriker(sniffer, fuzzer, logger)

    println("""
        ====================================================
        SOVEREIGN CORE PSC - VERSION 8.1 [INTERCEPTOR]
        STATUT : GRADE INDUSTRIEL MILITAIRE CERTIFIÉ
        ====================================================
    """.trimIndent())

    if (args.contains("--auto-engage")) {
        engine.switchMode("OFFENSIF")
        
        // Phase 1 : Écoute passive sur le port 8080 (Traffic Proxy)
        proxy.startService(8080)
        
        // Phase 2 : Riposte sur l'IP cible (Renseignement & Neutralisation)
        striker.initiateAdaptiveStrike("197.242.129.XX", 8888)
    } else {
        println("[i] Système en attente. Mode passif activé.")
    }
}
