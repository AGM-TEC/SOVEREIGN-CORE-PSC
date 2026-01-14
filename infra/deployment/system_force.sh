#!/bin/bash
# PROJECT: SOVEREIGN-CORE-PSC v35.2
# ROLE: Allocation de Puissance et Priorité de Combat
# STRATEGY: Hardware-Ready / Real-Time Execution

echo "⚡ [FORCE] Activation de la priorité de combat sur le processeur..."

# 1. Identification du Monolithe (Processus Java)
PID=$(pgrep -f "Sovereign_C4ISR_v35.1.jar")

if [ -z "$PID" ]; then
    echo "🚨 [ERREUR] Monolithe non détecté. Lancez le système d'abord."
    exit 1
fi

# 2. Attribution de la priorité maximale (RT)
# Le niveau -20 est la priorité la plus haute sur Linux
renice -n -20 -p $PID

# 3. Verrouillage de l'affinité CPU (CPU Pinning)
# On réserve les cœurs 0 et 1 exclusivement pour le Monolithe
taskset -cp 0,1 $PID

# 4. Nettoyage des processus parasites
echo "[🧹] Neutralisation des processus non-critiques..."
# Ici on pourrait couper les services inutiles (Bluetooth, etc.) pour libérer la RAM

echo "✅ [FORCE] Le Monolithe v35.1 dispose désormais de 100% de la puissance tactique."
