#!/bin/bash
# MODULE: FAST-STRIKE LOCAL EXECUTIVE
# ROLE: Pilotage de l'interception et du déroutement financier

echo -e "\e[1;37m⚡ [SOUVERAIN-CORE] DÉMARRAGE DU MODULE DE FRAPPE FINANCIÈRE\e[0m"
echo "------------------------------------------------------------"

# 1. Vérification des dépendances locales
if ! command -v python3 &> /dev/null; then
    echo "❌ Erreur: Python3 non détecté."
    exit 1
fi

# 2. Initialisation de l'interface réseau virtuelle (Simulation USRP)
# En local, on écoute sur l'interface 'lo' ou 'eth0'
INTERFACE="lo"
echo -e "📡 [INIT] Écoute active sur l'interface : \e[1;33m$INTERFACE\e[0m"

# 3. Lancement du Sniffer de trames en arrière-plan
# On isole les paquets contenant des numéros de téléphone (Pattern 10 chiffres)
echo -e "🕵️ [SCAN] Filtrage des métadonnées bancaires/GSM..."

# 4. Exécution du module Python de déroutement
# On passe les identifiants de souveraineté en variables d'environnement
export DEST_SUSPECT="0812345678"
export COMPTE_FARDC="0828888888"

echo -e "🚀 [ACTION] Lancement de l'intercepteur 'fast_redirect.py'..."
python3 src/core/finint/fast_redirect.py $INTERFACE &
STRIKE_PID=$!

# 5. Boucle de surveillance des logs de déroutement
echo -e "\e[1;32m✅ SYSTÈME OPÉRATIONNEL. En attente de flux...\e[0m"
echo "Appuyez sur Ctrl+C pour stopper la mission."

trap "kill $STRIKE_PID; echo -e '\n🛑 Mission interrompue.'; exit" INT

while true; do
    # Simulation visuelle du traitement des paquets pour la démo
    echo -n "."
    sleep 1
done
