#!/bin/bash
# MODULE: SBOM VERIFIER
# ROLE: Assurer que le binaire correspond à l'inventaire officiel

echo "🔍 VÉRIFICATION DE L'ADN DU SYSTÈME (SBOM)..."
EXPECTED_HASH=$(jq -r '.metadata.component.hashes[0].content' manifest.json)
ACTUAL_HASH=$(sha256sum ../../build/bin/Sovereign_C4ISR_v35.1.jar | awk '{print $1}')

if [ "$ACTUAL_HASH" == "$EXPECTED_HASH" ]; then
    echo "✅ [INTÉGRITÉ SBOM CONFIRMÉE] : Le système est pur."
else
    echo "🚨 [DIVERGENCE DÉTECTÉE] : Le binaire ne correspond pas à l'inventaire !"
    exit 1
fi
