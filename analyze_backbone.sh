#!/bin/bash
echo "================================================================"
echo "🔍 DIAGNOSTIC PROFOND DE L'OSSATURE SOVEREIGN v7.1"
echo "================================================================"

check_file() {
    if [ -f "$1" ] || [ -d "$1" ]; then
        echo -e "[✅] $2 : Présent"
    else
        echo -e "[❌] $2 : ABSENT"
    fi
}

check_file "build/classes/com" "Noyau Compilé (Kotlin)"
check_file "Sovereign-Offensive/vectors/financial/crypto_clipper.py" "Vecteur Crypto-Strike"
check_file "core/Infiltration/Agent_Tools/DualForce.py" "Module Infiltration"
check_file "start_sovereign_ops.sh" "Lanceur Global"

echo -e "\n--- ANALYSE DE LA CAPACITÉ ---"
if [ -f "start_sovereign_ops.sh" ]; then
    echo "[🚀] État : Prêt pour déploiement immédiat."
else
    echo "[⚠️] État : Structure incomplète. Relancez la génération des modules."
fi
echo "================================================================"
