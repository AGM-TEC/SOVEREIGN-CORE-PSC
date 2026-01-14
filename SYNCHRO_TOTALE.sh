#!/bin/bash
# =============================================================
# PROJECT: SOVEREIGN-CORE-PSC v35.2
# MODULE: SYNCHRONISATION TACTIQUE (Liaison Or)
# ROLE: Alignement du dépôt distant sur la Forge Souveraine
# =============================================================

echo "🛰️ INITIALISATION DE LA SYNCHRONISATION SOUVERAINE..."

# 1. Préparation des éléments de force
git add infra/
git add build/bin/Sovereign_C4ISR_v35.1.jar
git add build/bin/CERTIFICAT_V35_1.txt
git add FORCE_LA_FORGE.sh

# 2. Scellage du Commit avec marqueur de temps C4ISR
TIMESTAMP=$(date "+%Y-%m-%d %H:%M:%S")
git commit -m "🏛️ SYNCHRO : État Major v35.2 - Forge Scellée le $TIMESTAMP"

# 3. Propulsion vers le Cloud Souverain (GitHub)
echo "[🚀] Propulsion vers la branche v35-SOUVERAIN..."
git push origin v35-SOUVERAIN --force

echo "✅ SYNCHRONISATION TERMINÉE."
echo "🌍 Le système est désormais visible et prêt pour l'audit du CNS."
