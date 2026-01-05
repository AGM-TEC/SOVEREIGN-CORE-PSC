#!/bin/bash
# SOVEREIGN-CORE-PSC v5.2 - LANCEUR TACTIQUE SÉCURISÉ

echo "--- [🛡️] INITIALISATION DU SYSTÈME SOUVERAIN ---"

# 1. Génération de l'empreinte locale
LOCAL_ID=$(ls -al /data/data/com.termux/files/home | head -n 2 | md5sum | cut -c1-8)
FINGERPRINT="fa01a103" 

# 2. Vérification d'intégrité matérielle
if [[ "$LOCAL_ID" != "$FINGERPRINT" ]]; then
    echo "[❌] ERREUR : Environnement non autorisé. Accès refusé."
    echo "[⚠️] ALERTE : Nettoyage Anti-Forensics en cours..."
    # Protection des données sensibles
    rm -f test_wipe.log
    exit 1
fi

# 3. Lancement du Noyau v5.2
echo "[✅] Authentification réussie. Chargement du noyau v5.2..."
java -Dorg.jansi.skip=true      -cp "dist/SOVEREIGN-CORE-PSC-v5.2.jar:libs/*:libs/jakarta.servlet-api-5.0.0.jar"      com.fardc.sigint.core.MainKt
