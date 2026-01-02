package com.fardc.sigint.core.network
import java.net.Socket
import java.io.InputStream

class SdrBridge(val host: String = "127.0.0.1", val port: Int = 14423) {
    private var socket: Socket? = null
    private var input: InputStream? = null

    fun connect(): Boolean {
        return try {
            socket = Socket(host, port)
            input = socket?.getInputStream()
            true
        } catch (e: Exception) { false }
    }

    fun receiveSignal(): ByteArray {
        val buffer = ByteArray(2048)
        val read = input?.read(buffer) ?: -1
        return if (read > 0) buffer.copyOf(read) else ByteArray(0)
    }
}
