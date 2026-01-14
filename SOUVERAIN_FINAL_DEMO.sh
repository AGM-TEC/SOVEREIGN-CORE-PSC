#!/bin/bash
clear
echo -e "\e[1;37m🇨🇩 SYSTEME DE GUERRE TOTALE - COMMANDANT PSC v35.2\e[0m"
echo -e "\e[1;31m[MODE : SOUVERAINETÉ ABSOLUE ACTIVE]\e[0m"
echo "------------------------------------------------"
echo "1. SIGINT/ELINT (Écoute & Radar)"
echo "2. BFT (Suivi des Troupes ALPHA-15)"
echo "3. CYBER FININT (Interception Simple)"
echo "4. CYBER OPS (Défense & Attaque)"
echo -e "5. \e[1;31mFAST-STRIKE (Interception & Déroutement d'Urgence)\e[0m"
echo "------------------------------------------------"
read -p "SÉLECTIONNEZ LE THÈME DE DÉMONSTRATION : " choice

case $choice in
    1) ./INTERCEPT_SIGINT.sh ;;
    2) ./infra/deployment/national_board.sh ;;
    3) ./EXTRACT_FIN_SOUVERAIN.sh ;;
    4) ./SIMULATEUR_BROUILLAGE_M23.sh ;;
    5) ./CYBER_FIN_FAST_STRIKE.sh ;;
    *) echo "Choix invalide." ;;
esac
