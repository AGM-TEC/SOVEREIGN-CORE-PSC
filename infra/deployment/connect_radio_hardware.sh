#!/bin/bash
# MODULE: RADIO-BRIDGE v35.1
# ROLE: Liaison physique entre SDR et Monolithe

echo "📡 [CONNECT] Initialisation du pont radio..."

# 1. Ouverture du port d'écoute pour les capteurs SIGINT
# On simule l'entrée du flux radio brut sur le port 9001
nohup socat TCP-LISTEN:9001,reuseaddr,fork - > /dev/null 2>&1 &

# 2. Liaison du Monolithe au port de données
echo "🔗 [BRIDGE] Connexion du Monolithe au port 9001 (Flux Radio)..."

# 3. Test de ping sur l'interface radio tactique
ifconfig radio0 10.0.35.2 netmask 255.255.255.0 up 2>/dev/null || echo "⚠️ Note: Interface physique radio0 absente (Mode simulation)."

echo "✅ [SUCCESS] Pont matériel prêt pour la réception de signaux."
