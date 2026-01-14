#!/bin/bash
# PROJECT: SOVEREIGN-CORE-PSC v35.2
# MODULE: COSIGN (Autorité de Validation Zero-Trust)
# ROLE: Authentification du Monolithe avant engagement

BIN_PATH="../../build/bin/Sovereign_C4ISR_v35.1.jar"
CERT_PATH="../../build/bin/CERTIFICAT_V35_1.txt"

echo "🔐 [COSIGN] Initialisation de la vérification Zero-Trust..."

# 1. Vérification de la présence des composants
if [ ! -f "$BIN_PATH" ] || [ ! -f "$CERT_PATH" ]; then
    echo "🚨 [ALERTE CRITIQUE] : Composants de sécurité manquants !"
    exit 1
fi

# 2. Extraction du Hash scellé (Référence Or)
EXPECTED_HASH=$(cat "$CERT_PATH" | awk '{print $1}')

# 3. Calcul du Hash réel du binaire présent
ACTUAL_HASH=$(sha256sum "$BIN_PATH" | awk '{print $1}')

# 4. Comparaison et Décision de Tir (Launch Decision)
if [ "$ACTUAL_HASH" == "$EXPECTED_HASH" ]; then
    echo "✅ [AUTHENTIFIÉ] : Empreinte certifiée ($ACTUAL_HASH)."
    echo "🚀 Lancement du Monolithe en cours..."
    exit 0
else
    echo "🚨 [SABOTAGE DÉTECTÉ] : L'empreinte ne correspond pas !"
    echo "   Attendu : $EXPECTED_HASH"
    echo "   Trouvé   : $ACTUAL_HASH"
    echo "❌ Système verrouillé pour protection souveraine."
    exit 1
fi
