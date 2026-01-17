#!/data/data/com.termux/files/usr/bin/bash

echo "[*] --- DÉPLOIEMENT DU SYSTÈME SOUVERAIN SIGINT ---"

# 1. Capture via Kotlin (pilotage C++)
# Assurez-vous que l'USRP est branché et autorisé
./engage_monolithe.sh

if [ -f "capture_elint.dat" ]; then
    echo "[*] Phase 2 : Génération de l'analyse spectrale (Spectrogramme)..."
    python3 generate_elint_view.py
    
    if [ -f "elint_report.png" ]; then
        echo "[*] Phase 3 : Compilation du rapport officiel PDF..."
        python3 elint_report_gen.py
        
        # Nettoyage optionnel des fichiers volumineux
        # rm capture_elint.dat 
        
        echo "[+] MISSION RÉUSSIE : Rapport généré."
        # Ouverture pour inspection immédiate
        termux-open REPORT_SIGINT_*.pdf
    else
        echo "[!] ERREUR : Échec de la génération de l'image."
    fi
else
    echo "[!] ÉCHEC : Aucune donnée capturée. Vérifiez la liaison B210."
fi
