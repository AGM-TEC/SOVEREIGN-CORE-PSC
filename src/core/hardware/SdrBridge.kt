package core.hardware

import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.concurrent.thread

/**
 * SDR-BRIDGE v33.1 [MIL-SPEC]
 * Rôle: Interface d'abstraction matérielle avec le capteur radio.
 */
class SdrBridge {
    private var sdrProcess: Process? = null
    private var isScanning = false

    fun startSweep(minFreq: String, maxFreq: String, binSize: String) {
        if (isScanning) return
        println("[SDR-BRIDGE] Initialisation de la sonde ($minFreq - $maxFreq)...")
        
        try {
            // Appel système (Sera couplé à un driver TCP en condition réelle)
            val pb = ProcessBuilder("rtl_power", "-f", "\$minFreq:\$maxFreq:\$binSize", "-i", "1", "-e", "1h")
            pb.redirectErrorStream(true)
            sdrProcess = pb.start()
            isScanning = true

            thread(start = true) {
                val reader = BufferedReader(InputStreamReader(sdrProcess!!.inputStream))
                var line: String?
                println("[SIGINT] Capture du flux enclenchée.")
                
                while (reader.readLine().also { line = it } != null) {
                    // Transmission asynchrone pour éviter le Thermal Throttling
                }
                
                isScanning = false
                println("[SDR-BRIDGE] Fin de la capture.")
            }
        } catch (e: Exception) {
            println("[ERREUR] Échec de la liaison matérielle. Mode simulation requis.")
            isScanning = false
        }
    }

    fun haltSweep() {
        if (isScanning && sdrProcess != null) {
            sdrProcess!!.destroy()
            isScanning = false
        }
    }
}
