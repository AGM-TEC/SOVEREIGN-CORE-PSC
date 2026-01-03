package com.fardc.sigint.core
class SecurityVault {
    fun encryptData(data: String): String = "🔒[AES256]:$data"
    fun encrypt(data: String): String = encryptData(data)
}
