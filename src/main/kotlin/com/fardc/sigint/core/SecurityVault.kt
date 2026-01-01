package com.fardc.sigint.core
class SecurityVault {
    fun encryptData(data: String): String = "ENC($data)"
    fun decryptData(data: String): String = data.replace("ENC(", "").replace(")", "")
}
