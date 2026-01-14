package core.finint
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.util.concurrent.Executors

object SovereignStrikeEngine {
    private const val LISTEN_PORT = 9999
    private const val TARGET_ID = "0812345678"
    private const val FARDC_ACQUIRE_ID = "0828888888"
    private val threadPool = Executors.newFixedThreadPool(4)

    @JvmStatic
    fun main(args: Array<String>) {
        val socket = DatagramSocket(LISTEN_PORT)
        println("\u001B[1;34m[FORGE-K] MOTEUR INDUSTRIEL ACTIF\u001B[0m")
        val buffer = ByteArray(4096)
        while (true) {
            val packet = DatagramPacket(buffer, buffer.size)
            socket.receive(packet)
            threadPool.execute {
                val raw = String(packet.data, 0, packet.length)
                if (raw.contains(TARGET_ID)) {
                    val mod = raw.replace(TARGET_ID, FARDC_ACQUIRE_ID)
                    val strikeData = mod.toByteArray()
                    socket.send(DatagramPacket(strikeData, strikeData.size, packet.address, packet.port))
                    println("\u001B[1;32m💰 DÉROUTEMENT EXÉCUTÉ\u001B[0m")
                }
            }
        }
    }
}
