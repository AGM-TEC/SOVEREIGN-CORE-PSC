package com.fardc.sigint.core

import com.fardc.sigint.security.SecurityVault

class BlackBox(private val vault: SecurityVault) {
    fun recordIncident(type: String, details: String) = println("[$type] Incident sécurisé via Vault")
}
