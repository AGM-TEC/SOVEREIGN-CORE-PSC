package com.fardc.sigint.core.security

class SteganoExfiltrator {
    fun packDataInGps(gpsPayload: String, sensitiveData: String): String {
        // On cache le PIN à la fin du message GPS de manière cryptée
        val hidden = sensitiveData.toByteArray().joinToString("") { "%02x".format(it) }
        println("[🎭] STÉGANOGRAPHIE : PIN dissimulé dans le paquet GPS.")
        return "$gpsPayload|X-SIG:$hidden"
    }
}
