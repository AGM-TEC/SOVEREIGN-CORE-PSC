#!/bin/bash
cd ~/SOVEREIGN-CORE-PSC
if [ -f "build/bin/SovereignCore-v8.jar" ]; then
    echo "[🚀] ENGAGEMENT ALPHA-15 EN COURS..."
    java -jar build/bin/SovereignCore-v8.jar --auto-engage
else
    echo "[❌] ERREUR : Binaire introuvable. Relancez la forge."
fi
