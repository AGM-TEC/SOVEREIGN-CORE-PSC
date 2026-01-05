package com.fardc.sigint.core
import java.io.File
import java.time.LocalDateTime

class SecurityVault {
    // On écrit simplement dans le dossier courant
    private val database = File("mpesa_vault.db")

    fun encryptData(data: String) {
        try {
            val timestamp = LocalDateTime.now().toString()
            val entry = "[$timestamp] $data\n"
            database.appendText(entry)
            // Ce message DOIT apparaître dans votre console
            println("\n[🚨] ALERTE : DONNÉE REÇUE ET ENREGISTRÉE !")
            println("[📄] CONTENU : $data")
        } catch (e: Exception) {
            println("[❌] ERREUR D'ÉCRITURE : ${e.message}")
        }
    }
}
