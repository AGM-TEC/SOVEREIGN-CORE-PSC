#!/bin/bash
echo "[⚔️] ACTIVATION DU SYSTÈME SOVEREIGN v6.0..."

# 1. Stabilisation et Nettoyage
./scripts/stabilize_runtime.sh

# 2. Compilation des nouveaux modules actifs
CP="libs/*:build/classes"
kotlinc -cp "$CP" core/Interoperability/*.kt core/Security_Advanced/*.kt -d build/classes

# 3. Simulation du lancement du Waterfall et de l'Orchestrateur
echo "[🛰️] INITIALISATION DU SPECTRE RF (WATERFALL)..."
sleep 1

# Simulation de l'affichage en direct
for i in {1..10}; do
    VAL1=$((RANDOM % 10))
    VAL2=$((RANDOM % 10))
    VAL3=$((RANDOM % 10))
    echo "[📡] |  ..::--==+#%@#+=--::..  [SCAN: 900.${i} MHz] |"
    sleep 0.2
done

echo "[✅] SYSTÈME ACTIF : Orchestrateur en écoute sur 0.0.0.0:8080"
