package core

import core.state.CombatModeHandler
import core.state.CombatState
import core.security.BlackBox
import core.security.SecurityVault
import core.hardware.SdrBridge
import core.network.SignalStreamer
import core.intelligence.SignalClassifier
import modules.finint.PagoMovilInterceptor

fun main() {
    println("=======================================================")
    println("    SOVEREIGN-CORE v33.1 - DÉMONSTRATEUR TACTIQUE      ")
    println("    CIBLE DOCTRINALE : VENEZUELA (PROTECTION SEN)      ")
    println("    SECTEUR OPÉRATIONNEL : CARABOBO / TOCUYITO         ")
    println("=======================================================\n")

    // 1. Initialisation de l'infrastructure matérielle et logicielle
    val logger = BlackBox()
    val vault = SecurityVault(logger)
    val modeHandler = CombatModeHandler()
    val sdr = SdrBridge()
    val streamer = SignalStreamer(logger)
    val classifier = SignalClassifier(vault, modeHandler)
    val finInt = PagoMovilInterceptor(logger)

    // Initialisation du noyau cryptographique
    val masterKey = SecurityVault.generateHardwareEntropy(32)
    vault.initializeVault(masterKey)

    // 2. Transition Doctrinale
    println("\n[*] EN ATTENTE D'ORDRE DE COMMANDEMENT...")
    Thread.sleep(1000)
    val authKey = "AUTH-SOUVERAIN-X99"
    modeHandler.transitionTo(CombatState.OFFENSIVE, authKey)

    if (modeHandler.getCurrentState() == CombatState.OFFENSIVE) {
        // 3. Déploiement du bouclier (Réseau et SDR)
        println("\n[*] ACTIVATION DE LA MATRICE D'ACQUISITION...")
        streamer.initStream()
        sdr.startSweep("800M", "1000M", "1M")
        Thread.sleep(1500)

        // 4. Simulation d'un flux RF brut (Vecteur d'attaque entrant)
        println("\n[*] CAPTURE D'ANOMALIE RADIOFRÉQUENCE...")
        val simulatedRfData = byteArrayOf(0x0A, 0x0B, 0x0C, 0x0D)
        val aiReport = classifier.processInference(simulatedRfData)
        
        println(" -> Analyse IA  : Modulation = \${aiReport.modulation}")
        println(" -> Menace RF   : \${aiReport.anomalyDetected} (Indice de confiance : \${aiReport.confidence})")
        
        // Diffusion de l'alerte sur le réseau maillé
        streamer.broadcastData(simulatedRfData)

        // 5. Corrélation Financière (Extraction de la charge utile)
        if (aiReport.anomalyDetected) {
            println("\n[*] DÉCODAGE DE LA CHARGE UTILE (FinINT)...")
            val interceptedPayload = "TRAMA_REDE: 08A7B... VALIDACION_PAGO_MOVIL... DEST: 04141234567 ... MONTO: Bs. 4.500,00 ... REF: 99881"
            
            val isCritical = finInt.analyzePayload(interceptedPayload)

            if (isCritical) {
                println("\n[ACTION] Mise en quarantaine mathématique des preuves...")
                val encryptedData = vault.encrypt(interceptedPayload)
                println(" -> Sceau cryptographique (Base64) : \${encryptedData.take(45)}...")
            }
        }

        // 6. Procédure d'arrêt (Prévention du Thermal Throttling)
        println("\n[*] FIN DE L'ENGAGEMENT. PURGE DES SYSTÈMES.")
        sdr.haltSweep()
        streamer.stopStream()
    }

    println("\n=======================================================")
    println("                 CLÔTURE DU CYCLE OODA                 ")
    println("=======================================================")
}
