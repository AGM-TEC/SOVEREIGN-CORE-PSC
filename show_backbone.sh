#!/bin/bash
echo "================================================================"
echo "🏗️ ARCHITECTURE COMPLÈTE DU SYSTÈME SOVEREIGN v7.1"
echo "================================================================"

# Affichage de l'arborescence (exclut les dossiers de build pour la clarté)
if command -v tree > /dev/null; then
    tree -L 4 -I 'build|libs'
else
    find . -maxdepth 4 -not -path '*/.*' -not -path './build*' -not -path './libs*' | sed -e 's/[^-][^\/]*\// |/g' -e 's/|\([^ ]\)/|-\1/'
fi

echo -e "\n----------------------------------------------------------------"
echo "🔐 ÉTAT DES DROITS ET PERMISSIONS"
echo "----------------------------------------------------------------"

ls -l start_sovereign_ops.sh
ls -l SOVEREIGN-OPS-MANUAL.md
ls -l core/Security_Advanced/GhostWatchdog.sh

echo -e "\n----------------------------------------------------------------"
echo "🧠 ÉTAT DES BINAIRES COMPILÉS"
echo "----------------------------------------------------------------"
COUNT_CLASSES=$(find build/classes -name "*.class" | wc -l)
echo "Nombre de modules Kotlin compilés : $COUNT_CLASSES"

echo "================================================================"
