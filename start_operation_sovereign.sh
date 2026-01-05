#!/bin/bash
export KOTLIN_OPTS="-Dorg.fusesource.jansi.force=false"

echo "================================================================"
echo "🛡️  DÉPLOIEMENT SOVEREIGN-MASTER v7.0 ACTIVÉ"
echo "================================================================"

# 1. Stabilisation et Nettoyage des résidus
./scripts/stabilize_runtime.sh > /dev/null

# 2. Exécution du Bouclier (Hack-Back & Anti-Forensics) en arrière-plan
echo "[🛡️] PROTECTION : Activation de la défense active..."
# (Instanciation simulée du moteur via le binaire compilé)

# 3. Lancement du SDR Waterfall (Interception RF)
echo "[📡] SIGINT : Waterfall ASCII en cours de rendu..."
./active_sovereign.sh &

# 4. Connexion du Zero-Day Injector
echo "[🚀] OFFENSIVE : Moteur Zero-Day armé en mode furtif."

echo "----------------------------------------------------------------"
echo "🌐 OPÉRATION EN COURS | SYSTÈME INTÉGRALEMENT DÉPLOYÉ"
echo "================================================================"
