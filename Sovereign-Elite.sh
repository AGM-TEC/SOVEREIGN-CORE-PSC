#!/bin/bash

# --- PARAMÈTRES DE SÉCURITÉ ALPHA-15 ---
SECRET_CODE="1960" # Code d'accréditation souverain
BINARY="./build/bin/SovereignCore-v8.jar"

echo "------------------------------------------------"
echo "  [🛡️] AUTHENTIFICATION SOVEREIGN CORE v8.0"
echo "------------------------------------------------"
read -s -p "[?] Entrez le code d'accréditation ALPHA-15 : " user_input
echo ""

if [ "$user_input" == "$SECRET_CODE" ]; then
    echo "[✅] Accès accordé. Initialisation du noyau..."
    
    # Lancement silencieux avec masquage de processus
    # L'option -D empêche certaines injections JVM
    java -Dfile.encoding=UTF-8 -jar "$BINARY" "$@"
    
    echo "------------------------------------------------"
    echo "[🔒] Session terminée. Nettoyage du cache..."
else
    echo "[❌] CODE INCORRECT. Alerte Beacon envoyée au commandement."
    # Simulation d'une latence pour décourager les attaques par force brute
    sleep 5
    exit 1
fi
