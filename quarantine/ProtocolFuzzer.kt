package com.fardc.sigint.core

import java.net.InetSocketAddress
import java.net.Socket
import java.security.SecureRandom
import java.util.concurrent.Executors

class ProtocolFuzzer(private val logger: BlackBox, private val brain: StateMachine) {
    
    private val random = SecureRandom()
    private val pool = Executors.newFixedThreadPool(5) // Multi-threading pour saturation

    fun launchAdvancedFuzzing(target: String, port: Int, intensity: Int = 100) {
        if (brain.current_mode != "OFFENSIF") {
            println("[🚫] FUZZER : Autorisation refusée. Mode OFFENSIF requis.")
            return
        }

        println("[🚀] V8.0 FUZZER : Saturation tactique amorcée sur $target:$port.")
        logger.record_incident("FUZZ_STRIKE", "Cible: $target | Intensité: $intensity")

        repeat(intensity) {
            pool.execute {
                executeFuzzCycle(target, port)
            }
        }
    }

    private fun executeFuzzCycle(target: String, port: Int) {
        try {
            val socket = Socket()
            socket.connect(InetSocketAddress(target, port), 500)
            
            val output = socket.getOutputStream()
            
            // Stratégie v8.0 : Alternance entre données aléatoires et patterns de crash (Nulls, Long Strings)
            val payload = when (random.nextInt(3)) {
                0 -> ByteArray(2048).apply { random.nextBytes(this) } // Junk brut
                1 -> "A".repeat(4096).toByteArray() // Buffer Overflow pattern
                else -> "\u0000".repeat(1024).toByteArray() // Null byte injection
            }

            output.write(payload)
            output.flush()
            socket.close()
        } catch (e: Exception) {
            // La cible commence à flancher ou refuse les connexions
        }
    }
}
