#!/bin/bash
# PROJECT: SOVEREIGN-CORE-PSC v35.2
# MODULE: COSIGN - VÉRIFICATION DE SIGNATURE SOUVERAINE

MONOLITH_BIN="$HOME/SOVEREIGN-CORE-PSC/build/bin/Sovereign_C4ISR_v35.1.jar"
CERTIFICATE="$HOME/SOVEREIGN-CORE-PSC/build/bin/CERTIFICAT_V35_1.txt"

echo "🔐 VÉRIFICATION DE LA SIGNATURE DE SOUVERAINETÉ..."

if [ ! -f "$MONOLITH_BIN" ] || [ ! -f "$CERTIFICATE" ]; then
    echo "❌ [ERREUR CRITIQUE] : Composants de démarrage manquants."
    exit 1
fi

EXPECTED_HASH=$(cat "$CERTIFICATE" | awk '{print $1}')
ACTUAL_HASH=$(sha256sum "$MONOLITH_BIN" | awk '{print $1}')

if [ "$ACTUAL_HASH" == "$EXPECTED_HASH" ]; then
    echo "✅ [AUTHENTIFIÉ] : Signature valide."
    java -jar "$MONOLITH_BIN"
else
    echo "🚨 [ALERTE SÉCURITÉ] : BINAIRE NON SIGNÉ OU CORROMPU !"
    exit 1
fi
