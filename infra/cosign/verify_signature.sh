#!/bin/bash
# MODULE: COSIGN - VÉRIFICATION
MONOLITH_BIN="$HOME/SOVEREIGN-CORE-PSC/build/bin/Sovereign_C4ISR_v35.1.jar"
CERTIFICATE="$HOME/SOVEREIGN-CORE-PSC/build/bin/CERTIFICAT_V35_1.txt"
EXPECTED_HASH=$(cat "$CERTIFICATE" | awk '{print $1}')
ACTUAL_HASH=$(sha256sum "$MONOLITH_BIN" | awk '{print $1}')
if [ "$ACTUAL_HASH" == "$EXPECTED_HASH" ]; then
    echo "✅ Signature Valide."
    java -jar "$MONOLITH_BIN"
else
    echo "🚨 Erreur Signature !"
    exit 1
fi
