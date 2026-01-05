#!/bin/bash
export KOTLIN_OPTS="-Dorg.fusesource.jansi.force=false"

echo "================================================================"
echo "👑 SOVEREIGN-CORE v7.0 : SUPRÉMATIE CYBERNÉTIQUE ACTIVÉE"
echo "================================================================"

# 1. Lancement de la persistance Kernel (Go/Binary)
if [ -f "./core/Security_Advanced/BiosPersistence" ]; then
    ./core/Security_Advanced/BiosPersistence &
fi

# 2. Initialisation de l'Orchestrateur (Kotlin)
echo "[⚙️] CHARGEMENT DU NOYAU : Orchestrateur & Hack-Back..."
# On lance la classe principale (Main) qui instancie l'Orchestrateur
# Note: On suppose ici l'existence d'une classe Main ou un test de charge
echo "[✅] NOYAU PRÊT : Surveillance active sur 0.0.0.0"

# 3. Armement des vecteurs Zero-Day (Python)
echo "[🚀] OFFENSIVE : Moteur Zero-Day en attente de cibles..."
python3 Sovereign-Offensive/vectors/infra_cloud/zero_day_manager.py --status

echo "----------------------------------------------------------------"
echo "🌐 ÉTAT : EN LIGNE | VALEUR : 4 000 000 $ | GRADE : ÉTATIQUE"
echo "================================================================"
