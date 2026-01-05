package core.audit
object MissionLogger {
    fun info(msg: String) = println("[INFO] $msg")
    fun critical(msg: String) = println("[CRITICAL] $msg")
}
