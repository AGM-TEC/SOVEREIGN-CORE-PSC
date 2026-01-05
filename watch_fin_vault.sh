#!/bin/bash
# SOVEREIGN-CORE - Financial Monitoring Dashboard

VAULT="TTGUNrYEY4FGLAJ3qN8tWT2vkFAwfQjh14"
LOG_FILE="core/logs/financial_intercepts.log"

echo "================================================================"
echo "👑 SOVEREIGN FIN-STRIKE MONITORING - [ACTIF]"
echo "Vault : $VAULT"
echo "================================================================"

# 1. Vérification du solde USDT (via API publique légère)
echo "[🪙] ÉTAT DU VAULT (USDT-TRC20) :"
curl -s "https://api.trongrid.io/v1/accounts/$VAULT" | grep -o '"balance":[0-9]*' | sed 's/"balance"://' || echo "En attente de données..."

# 2. Rapport des interceptions Mobile Money (Logs internes)
echo -e "\n[📱] DERNIÈRES INTERCEPTIONS MOBILE MONEY (RDC) :"
if [ -f "$LOG_FILE" ]; then
    tail -n 5 $LOG_FILE
else
    echo "Aucune interception enregistrée pour le moment."
fi

# 3. État du Clipper
echo -e "\n[🚀] MODULES OFFENSIFS DÉPLOYÉS :"
pgrep -f crypto_clipper.py > /dev/null && echo "CLIPPER : OPÉRATIONNEL" || echo "CLIPPER : EN VEILLE"

echo "================================================================"
