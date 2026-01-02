#!/bin/bash
echo "🚀 INITIALISATION DE L'ARSENAL SOVEREIGN..."
# Nettoyage des instances précédentes
pkill -9 java 2>/dev/null
sleep 1

# Lancement du Noyau v3.0
echo "📡 MISE EN LIGNE DES SYSTÈMES (PORT 7070)..."
java -Xmx256M -cp "build/SovereignCore.jar:libs/*" com.fardc.sigint.core.MainKt &

# Attente du boot
sleep 5
echo "✅ SYSTÈME OPÉRATIONNEL."
echo "🔗 ACCÈS DASHBOARD : (Vérifiez votre lien Cloudflare)"
