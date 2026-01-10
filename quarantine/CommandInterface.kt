package com.fardc.sigint.core

import java.util.Scanner

/**
 * INTERFACE DE COMMANDEMENT PSC
 * Permet le pilotage des modules offensifs et cyber
 */
class CommandInterface(
    private val scanner: OffensiveScanner,
    private val cyber: FinCapInterception
) {
    fun start() {
        val input = Scanner(System.`in`)
        println("\n🛡️ UNITÉ DE COMMANDEMENT SOUVERAINE ACTIVE")
        
        while (true) {
            println("\n[MENU] 1: Scan de ports | 2: Interception Cyber | 3: Quitter")
            print("PSC-CMD > ")
            
            when (input.nextLine()) {
                "1" -> {
                    print("Cible IP : ")
                    val ip = input.nextLine()
                    scanner.performPortScan(ip, 20, 1000)
                }
                "2" -> cyber.runInterceptionMode()
                "3" -> break
                else -> println("Commande non reconnue.")
            }
        }
    }
}
