package com.fardc.sigint.core

import javax.crypto.Cipher
import java.net.ServerSocket

fun main() {
    println("🛡️ SOVEREIGN-CORE-PSC | SYSTÈME D'ARMEMENT")
    println("🔍 Diagnostic de l'environnement en cours...")

    val isNetworkOk = try {
        ServerSocket(0).use { true }
    } catch (e: Exception) { false }

    val isCryptoOk = try {
        Cipher.getInstance("AES") != null
    } catch (e: Exception) { false }

    println("📡 Réseau Bas-Niveau : ${if (isNetworkOk) "✅ OPÉRATIONNEL" else "❌ BLOQUÉ"}")
    println("🔐 Chiffrement AES : ${if (isCryptoOk) "✅ OPÉRATIONNEL" else "❌ BLOQUÉ"}")

    if (isNetworkOk && isCryptoOk) {
        println("🚀 TOUT EST OK. PASSAGE EN MODE COMBAT ACTIF.")
        // Lancer ici vos modules réels
    } else {
        println("🚨 ÉTAT CRITIQUE : L'environnement GitHub bride toujours les capacités.")
    }
}
package com.fardc.sigint.core

import io.javalin.Javalin // Añadir esta dependencia al build.gradle.kts

fun main() {
    val app = Javalin.create().start(7070)
    
    app.get("/") { ctx -> 
        ctx.html("<h1>🛡️ Mando Soberano PSC</h1>" +
                 "<button onclick=\"fetch('/active/sniffer')\">Activar Sniffer</button>" +
                 "<button onclick=\"fetch('/active/jammer')\">Activar Jammer</button>")
    }

    app.get("/active/sniffer") { ctx ->
        PacketSniffer().startSniffing(8080)
        ctx.result("📡 Sniffer en curso...")
    }
}
