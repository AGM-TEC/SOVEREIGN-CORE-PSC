package core.security

class BlackBox {
    fun recordIncident(tag: String, details: String) {
        println("[BLACKBOX-AUDIT] $tag : $details")
    }
}
