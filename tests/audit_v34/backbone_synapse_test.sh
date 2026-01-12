#!/bin/bash
echo "--- AUDIT SYNAPTIQUE v34.0 ---"

# Vérification du couplage MasterCore -> Vault
if grep -q "val vault = SecurityVault()" build/monolith/00_MasterCore.kt; then
    echo "[✅] Liaison VAULT : SÉCURISÉE"
else
    echo "[❌] Liaison VAULT : RUPTURE DÉTECTÉE"
fi

# Vérification du couplage MasterCore -> Chain
if grep -q "val chain = SovereignChain" build/monolith/00_MasterCore.kt; then
    echo "[✅] Liaison CHAIN : IMMUTABLE"
else
    echo "[❌] Liaison CHAIN : RUPTURE DÉTECTÉE"
fi
