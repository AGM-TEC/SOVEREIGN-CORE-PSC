package core.intelligence

import java.util.concurrent.ConcurrentHashMap

object TrafficHeuristicAnalyzer {
    
    // Matrice de signatures (Volume de paquets typique pour une transaction)
    // Règle de recoupement : Ces données doivent être calibrées sur le terrain.
    private const val SWIFT_BURST_MIN_BYTES = 4500
    private const val SWIFT_BURST_MAX_BYTES = 5200
    private const val MPESA_BURST_BYTES = 1200
    
    // Suivi de l'état des connexions par adresse IP source
    private val connectionStates = ConcurrentHashMap<String, Int>()

    fun analyzePacketMetadata(sourceIp: String, destinationSni: String, packetSizeBytes: Int) {
        // 1. Filtrage spatial : Isoler le trafic vers les infrastructures financières connues
        if (destinationSni.contains("swift.net") || destinationSni.contains("mpesa")) {
            
            // 2. Accumulation de la masse de données (Intégration temporelle)
            val currentVolume = connectionStates.getOrDefault(sourceIp, 0) + packetSizeBytes
            connectionStates[sourceIp] = currentVolume

            // 3. Évaluation de la signature de transaction (Analyse Heuristique)
            evaluateTransactionSignature(sourceIp, destinationSni, currentVolume)
        }
    }

    private fun evaluateTransactionSignature(targetIp: String, sni: String, volume: Int) {
        if (sni.contains("swift") && volume in SWIFT_BURST_MIN_BYTES..SWIFT_BURST_MAX_BYTES) {
            println("[FinINT] ALERTE HEURISTIQUE : Signature de transaction SWIFT détectée sur IP $targetIp (Volume: $volume octets).")
            resetState(targetIp)
            // L'alerte doit être routée vers le TacticalDashboard
        } else if (sni.contains("mpesa") && volume > MPESA_BURST_BYTES) {
            println("[FinINT] ALERTE HEURISTIQUE : Signature de transfert Mobile Money détectée sur IP $targetIp.")
            resetState(targetIp)
        }
    }

    private fun resetState(ip: String) {
        connectionStates.remove(ip)
    }
}
