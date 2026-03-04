package core.network

import java.util.concurrent.ConcurrentSkipListMap

class TacticalRingBuffer(private val maxCapacityBytes: Long) {
    // Structure de données triée par priorité et horodatage
    private val buffer = ConcurrentSkipListMap<Long, ByteArray>()
    private var currentSizeBytes: Long = 0

    @Synchronized
    fun enqueue(priority: Int, payload: ByteArray) {
        val payloadSize = payload.size.toLong()
        
        // Règle de physique mémoire : Éviction stricte si la capacité est dépassée
        while (currentSizeBytes + payloadSize > maxCapacityBytes && buffer.isNotEmpty()) {
            val oldestKey = buffer.firstKey()
            currentSizeBytes -= buffer[oldestKey]?.size?.toLong() ?: 0L
            buffer.remove(oldestKey)
        }
        
        // La clé combine la priorité (poids fort) et l'horodatage (poids faible)
        // Les priorités basses (ex: logs) ont des clés plus petites et sont évincées en premier
        val key = (priority.toLong() shl 48) or (System.currentTimeMillis() and 0xFFFFFFFFFFFFL)
        buffer[key] = payload
        currentSizeBytes += payloadSize
    }

    fun extractAll(): List<ByteArray> {
        val data = buffer.values.toList()
        buffer.clear()
        currentSizeBytes = 0
        return data
    }
}
