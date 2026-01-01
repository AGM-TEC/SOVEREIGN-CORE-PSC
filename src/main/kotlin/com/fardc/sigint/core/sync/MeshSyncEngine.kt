package com.fardc.sigint.core.sync

import com.fardc.sigint.core.CombatModeHandler
import com.fardc.sigint.core.CombatState
import com.fardc.sigint.core.SecurityVault
import io.javalin.Javalin
import java.time.Instant

class MeshSyncEngine(private val vault: SecurityVault, private val combatHandler: CombatModeHandler) {
    fun setup(app: Javalin) {
        app.post("/mesh/sync") { ctx ->
            if (combatHandler.getStatus() == CombatState.STBY) {
                ctx.status(403).result("MESH_LOCKED")
                return@post
            }
            vault.encryptData("MESH_RCV: ${ctx.body()}")
            ctx.result("ACK")
        }
    }
}
