package com.fardc.sigint.core.interop

import java.nio.ByteBuffer
import java.nio.ByteOrder

class HighPerformanceBuffer(private val capacity: Int) {
    // Allocation hors-heap pour éviter les pauses du Garbage Collector
    private val buffer: ByteBuffer = ByteBuffer.allocateDirect(capacity).order(ByteOrder.nativeOrder())

    fun writeSample(data: ByteArray) {
        if (buffer.remaining() < data.size) {
            buffer.clear() // Buffer circulaire : on écrase les plus vieilles données
            println("[🧹] BUFFER : Rotation du tampon mémoire (Overload Protection)")
        }
        buffer.put(data)
    }

    fun getBufferStatus(): String {
        return "Capacité: ${capacity} bytes | Position: ${buffer.position()}"
    }
}
