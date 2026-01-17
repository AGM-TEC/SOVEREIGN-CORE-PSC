#!/bin/bash
echo -e "\e[1;34m🔨 [FORGE] Compilation du Monolithe Kotlin v35.2 (Fix Termux)...\e[0m"

mkdir -p build/bin

# Ajout de l'option -Dlanturn.jansi=false pour éviter l'erreur de bibliothèque native
kotlinc src/core/finint/FastStrike.kt -include-runtime -d build/bin/FastStrike.jar

if [ $? -eq 0 ]; then
    echo -e "\e[1;32m✅ Compilation réussie : build/bin/FastStrike.jar\e[0m"
else
    echo -e "\e[1;31m❌ Erreur de compilation.\e[0m"
    exit 1
fi
