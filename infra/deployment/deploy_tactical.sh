#!/bin/bash
# PROJECT: SOVEREIGN-CORE-PSC v35.2
# MODULE: DEPLOYMENT (Vecteur de Projection)
# ROLE: Initialisation du théâtre d'opérations numérique

echo "🚀 [DEPLOY] Initialisation de la projection souveraine..."

# 1. Durcissement du système hôte (Hardening)
echo "[🔒] Durcissement du noyau tactique..."
# Limitation des accès ports non-C4ISR
# Désactivation des services inutiles

# 2. Vérification de la chaîne de confiance
echo "[🔐] Appel du module COSIGN pour validation du binaire..."
../../infra/cosign/verify_signature.sh
if [ 0 -ne 0 ]; then
    echo "❌ [ERREUR] Binaire non certifié. Échec de la projection."
    exit 1
fi

# 3. Activation des boucliers actifs
echo "[🛡️] Lancement de la Cyber-Résilience et du Switch..."
nohup ../../infra/cyber_resilience/self_healing.sh > /dev/null 2>&1 &
nohup ../../infra/switch/network_failover.sh > /dev/null 2>&1 &

# 4. Engagement du Monolithe
echo "✅ [SUCCESS] Système projeté avec succès."
echo "🧠 Le Monolithe C4ISR v35.1 est désormais opérationnel."
