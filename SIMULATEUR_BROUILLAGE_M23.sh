#!/bin/bash
echo -e "\e[1;31m🚨 [ALERTE] DÉBUT DE L'ATTAQUE ÉLECTRONIQUE (SIMULATION LOGIQUE)...\e[0m"
sleep 1

# Au lieu de couper une interface IP, on crée un fichier "PANNE"
echo "STATE=DOWN" > .link_eth0_status

echo -e "\e[1;31m[DROP] Liaison Primaire (Fibre) INTERCEPTÉE par l'ennemi.\e[0m"
sleep 2

echo -e "\e[1;32m🧠 [MONOLITHE] Analyse du trafic... Échec détecté.\e[0m"
echo -e "\e[1;36m🛰️ [SWITCH] Bascule automatique sur le tunnel Satellite ALPHA-SOUVERAIN.\e[0m"
sleep 1
echo -e "\e[1;32m✅ [SUCCESS] Communication rétablie sur 192.168.100.1 (Interface Virtuelle).\e[0m"

# Nettoyage
rm .link_eth0_status
