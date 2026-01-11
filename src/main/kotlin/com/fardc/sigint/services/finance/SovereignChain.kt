package com.fardc.sigint.services.finance

import com.fardc.sigint.core.BlackBox
import java.security.MessageDigest

/**
 * SOVEREIGN-CHAIN v24.7 [AXE 4 - INTEGRITY]
 * Standard: Private Military Ledger (PoA)
 * Rôle: Immuabilité des flux logistiques et financiers.
 */
class SovereignChain(private val logger: BlackBox) {

    data class Block(val index: Int, val timestamp: Long, val data: String, val previousHash: String) {
        val hash: String = calculateHash()
        
        fun calculateHash(): String {
            val input = "$index$timestamp$data$previousHash"
            return MessageDigest.getInstance("SHA-256")
                .digest(input.toByteArray())
                .joinToString("") { "%02x".format(it) }
        }
    }

    private val chain = mutableListOf<Block>()

    init {
        // Bloc Genèse (Sceau de l'État-Major)
        chain.add(Block(0, System.currentTimeMillis(), "GENESIS_SECURE_LOGISTICS", "0"))
    }

    fun logAssetMovement(assetId: String, origin: String, destination: String) {
        val data = "ASSET:$assetId | FROM:$origin | TO:$destination | STATUS:MOVED"
        val newBlock = Block(chain.size, System.currentTimeMillis(), data, chain.last().hash)
        chain.add(newBlock)
        
        println("[⛓️] BLOCKCHAIN : Mouvement d'actif sécurisé dans le bloc #${newBlock.index}")
        println("[🔐] HASH : ${newBlock.hash.take(16)}...")
        logger.recordIncident("BLOCKCHAIN_ENTRY", "Asset $assetId moved to $destination")
    }

    fun verifyIntegrity(): Boolean {
        for (i in 1 until chain.size) {
            if (chain[i].previousHash != chain[i-1].hash) return false
        }
        return true
    }
}
