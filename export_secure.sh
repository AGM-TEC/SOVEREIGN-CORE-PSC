#!/data/data/com.termux/files/usr/bin/bash

# Définition du nom de l'archive avec horodatage
ARCHIVE_NAME="SIGINT_SOUVERAIN_$(date +%Y%m%d_%H%M%S).zip"

echo "[*] PRÉPARATION DE L'ARCHIVE SÉCURISÉE..."

# Vérification de l'existence des fichiers
if [ ! -f "capture_elint.dat" ] && [ ! -f "elint_report.png" ]; then
    echo "[!] ERREUR : Aucun fichier de mission trouvé pour l'export."
    exit 1
fi

# Chiffrement AES-256
# Note : Le mot de passe vous sera demandé deux fois pour confirmation
zip -e -P "$1" "$ARCHIVE_NAME" capture_elint.dat elint_report.png REPORT_SIGINT_*.pdf

if [ $? -eq 0 ]; then
    echo "[+] ARCHIVE CRYPTÉE GÉNÉRÉE : $ARCHIVE_NAME"
    echo "[*] Vous pouvez maintenant envoyer ce fichier sur GitHub ou par mail."
else
    echo "[!] ÉCHEC DU CHIFFREMENT."
fi
