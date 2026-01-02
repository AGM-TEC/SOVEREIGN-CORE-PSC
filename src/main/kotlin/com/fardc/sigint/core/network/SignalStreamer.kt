package com.fardc.sigint.core.network

import io.javalin.websocket.WsContext
import java.util.concurrent.ConcurrentHashMap

class SignalStreamer {
    private val sessions = ConcurrentHashMap.newKeySet<WsContext>()

    fun setup(app: io.javalin.Javalin) {
        app.ws("/api/signals/live") { ws ->
            ws.onConnect { ctx -> sessions.add(ctx) }
            ws.onClose { ctx -> sessions.remove(ctx) }
            ws.onError { ctx -> sessions.remove(ctx) }
        }
    }

    fun broadcastSpectrumData(data: String) {
        // Version ultra-compatible : on envoie et on nettoie si échec
        val iterator = sessions.iterator()
        while (iterator.hasNext()) {
            val session = iterator.next()
            try {
                session.send(data)
            } catch (e: Exception) {
                iterator.remove()
            }
        }
    }
}
