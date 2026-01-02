#!/bin/bash
# 🛡️ SOVEREIGN CORE v4.0 - DEPLOYMENT SCRIPT (FARDC-SIGINT)

echo "🚀 Démarrage de la Station de Commandement v4.0..."

# 1. Vérification des ponts SDR
if pgrep -x "rtl_tcp" > /dev/null
then
    echo "📡 [SDR] Driver RTL_TCP détecté."
else
    echo "⚠️ [SDR] Driver local non détecté. Passage en mode interception réseau."
fi

# 2. Lancement du Noyau
java -cp "bin/SovereignCore.jar:libs/*" com.fardc.sigint.core.MainKt
