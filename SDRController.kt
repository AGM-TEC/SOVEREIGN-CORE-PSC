import java.io.File

class MonolitheSIGINT {
    fun engage() {
        println("[*] [MONOLITHE] Recherche du vecteur d'interception...")
        
        // Tentative 1 : Binaire C++ (Intercepteur)
        val interceptor = File("/data/data/com.termux/files/usr/bin/uhd_interceptor")
        
        if (interceptor.exists()) {
            println("[+] Vecteur C++ détecté. Lancement de la capture...")
            runProcess("uhd_interceptor", "--args", "type=b200", "--freq", "940e6", "--nsamps", "1000000")
        } else {
            println("[!] Vecteur C++ absent. Basculement sur l'API Python Souveraine...")
            // On appelle le script python que nous avons préparé plus tôt
            runProcess("python", "monolithe_sigint.py")
        }
    }

    private fun runProcess(vararg cmd: String) {
        try {
            ProcessBuilder(*cmd).inheritIO().start().waitFor()
        } catch (e: Exception) {
            println("[!] ÉCHEC D'ENGAGEMENT : ${e.message}")
        }
    }
}

fun main() = MonolitheSIGINT().engage()
