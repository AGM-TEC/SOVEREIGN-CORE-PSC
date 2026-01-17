#!/data/data/com.termux/files/usr/bin/bash
echo "--- DIAGNOSTIC DE SANTÉ MONOLITHE CORE ---"

# 1. Vérification Pilote RF
if uhd_config_info --version > /dev/null 2>&1; then
    echo "[+] Pilote UHD : OPÉRATIONNEL ($(uhd_config_info --version | head -n 1))"
else
    echo "[!] Pilote UHD : ÉCHEC"
fi

# 2. Vérification Forge Kotlin
if command -v kotlinc > /dev/null; then
    echo "[+] Compilateur Kotlin : PRÊT"
else
    echo "[!] Compilateur Kotlin : MANQUANT"
fi

# 3. Vérification Analyseur Python
python3 -c "import numpy; import matplotlib; import reportlab" > /dev/null 2>&1
if [ $? -eq 0 ]; then
    echo "[+] Suite d'Analyse Python : ARMÉE"
else
    echo "[!] Suite d'Analyse Python : DÉPENDANCES MANQUANTES"
fi

# 4. Vérification Liaison GitHub
current_branch=$(git rev-parse --abbrev-ref HEAD)
echo "[+] Branche active : $current_branch"
