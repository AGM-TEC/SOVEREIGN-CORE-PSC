package com.fardc.sigint.core.security

import kotlin.random.Random

class HoneyNetGenerator {
    fun generateFakeInterception(): String {
        val fakeUsers = listOf("admin@mpesa.cd", "test_user@vodacom.cd", "target_01@gmail.com")
        val fakePin = Random.nextInt(1000, 9999)
        val data = "[💰] HONEY-DATA: User=${fakeUsers.random()} | PIN=$fakePin"
        
        println("[🎭] DECEPTION: Envoi de fausses données d'interception vers les logs.")
        return data
    }
}
