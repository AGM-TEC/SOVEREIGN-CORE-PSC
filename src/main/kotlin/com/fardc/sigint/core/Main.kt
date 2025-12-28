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
