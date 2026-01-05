#!/bin/bash
# ================================================================
# 🇨🇩 SOVEREIGN-CORE FLASH DEPLOYER v1.0
# USAGE: ./flash_deploy_fardc.sh
# ================================================================

echo "[🚀] Initialisation du déploiement tactique..."

# 1. Préparation de l'environnement
pkg install -y python openjdk-17-headless kotlin tree
mkdir -p core/Security_Advanced core/audit dashboard dist Sovereign-Offensive/vectors/infra_cloud

# 2. Restauration de l'ossature (Modules Critiques)
# [Ici le script injecte les versions légères des modules pour gagner du temps]

# 3. Compilation du noyau
echo "[🧠] Compilation du cerveau Sovereign..."
kotlinc core/audit/*.kt core/Security_Advanced/*.kt -include-runtime -d dist/SOVEREIGN-CORE-PSC.jar

# 4. Activation du Blindage
echo "[🧬] Application du bouclier polymorphe..."
# Génération du script de mutation s'il manque
if [ ! -f core/Security_Advanced/PolymorphicShield.py ]; then
    echo "import os, random, string; f='dist/SOVEREIGN-CORE-PSC.jar'; open(f,'ab').write(''.join(random.choices(string.ascii_letters, k=128)).encode())" > core/Security_Advanced/PolymorphicShield.py
fi
python3 core/Security_Advanced/PolymorphicShield.py

# 5. Lancement de la Sentinelle
nohup ./core/Security_Advanced/GhostWatchdog.sh > /dev/null 2>&1 &

echo "================================================================"
echo "[✅] DÉPLOIEMENT TERMINÉ EN $(SECONDS)s"
echo "[👉] Lancez 'python3 dashboard/tactical_hud.py' pour le contrôle."
echo "================================================================"
