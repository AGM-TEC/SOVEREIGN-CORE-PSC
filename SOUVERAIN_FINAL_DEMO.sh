#!/bin/bash
clear
echo "🇨🇩 SYSTEME DE GUERRE TOTALE - COMMANDANT PSC"
echo "------------------------------------------------"
echo "1. SIGINT/ELINT (Écoute & Radar)"
echo "2. BFT (Suivi des Troupes)"
echo "3. CYBER FININT (Traque Financière)"
echo "4. CYBER OPS (Défense & Attaque)"
echo "------------------------------------------------"
read -p "SÉLECTIONNEZ LE THÈME DE DÉMONSTRATION : " choice

case $choice in
    1) ./INTERCEPT_SIGINT.sh ;;
    2) ./infra/deployment/national_board.sh ;;
    3) echo "🔍 Scan des flux financiers en cours..." ; sleep 2 ; echo "✅ Cibles financières verrouillées." ;;
    4) ./SIMULATEUR_BROUILLAGE_M23.sh ;;
    *) echo "Choix invalide." ;;
esac
