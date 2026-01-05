package com.fardc.sigint.core.security

class SignalJammer {
    // Utilisation de listes à taille fixe pour éviter l'explosion de la RAM
    private val signalLogs = Array<String?>(100) { null }
    private var index = 0

    fun processSignal(data: String) {
        // Écrase les anciennes données pour maintenir une empreinte mémoire constante
        signalLogs[index] = data
        index = (index + 1) % 100
        
        // Nettoyage explicite périodique
        if (index == 0) {
            System.gc() 
            println("[🧹] RAM OPTIMIZATION: Nettoyage des résidus de signal effectué.")
        }
    }
}
