package com.fardc.sigint.core

import java.net.URL
import java.net.HttpURLConnection
import java.util.Base64

/**
 * MODULE DATA-EXFILTRATION PSC
 * Exportation sécurisée des journaux SIGINT
 */
class DataExfiltrator(private val vault: SecurityVault) {
    fun exfiltrate(data: String, destination: String) {
        println("[EXFIL] Préparation de l'exportation vers $destination...")
        try {
            val encryptedData = vault.encryptData(data)
            val url = URL(destination)
            val connection = url.openConnection() as HttpURLConnection
            
            connection.requestMethod = "POST"
            connection.doOutput = true
            connection.setRequestProperty("Content-Type", "application/octet-stream")
            
            connection.outputStream.use { it.write(encryptedData.toByteArray()) }
            
            if (connection.responseCode == 200) {
                println("[SUCCESS] Paquet SIGINT exporté avec succès.")
            }
            connection.disconnect()
        } catch (e: Exception) {
            println("[ERROR] Échec de l'exfiltration : ${e.message}")
        }
    }
}
