#!/bin/bash
echo "🛡️ SOVEREIGN-CORE-PSC | Préparation de la Release v2.3.0"
echo "------------------------------------------------------"

# 1. Création des dossiers nécessaires
mkdir -p build/libs
mkdir -p dist/

# 2. Compilation via le compilateur Kotlin natif (Optimisé Termux)
echo "⚙️ Compilation des modules..."
kotlinc src/main/kotlin/com/fardc/sigint/core/*.kt \
-cp "libs/*" \
-include-runtime -d build/libs/sigint-core-all.jar

if [ $? -eq 0 ]; then
    echo "✅ Compilation réussie."
    
    # 3. Packaging pour la distribution
    echo "📦 Création du package de release..."
    cp build/libs/sigint-core-all.jar dist/
    cp -r docs dist/ 2>/dev/null
    
    echo "------------------------------------------------------"
    echo "🚀 RELEASE PRÊTE : dist/sigint-core-all.jar"
    echo "DÉPLOIEMENT : java -cp 'dist/sigint-core-all.jar:libs/*' com.fardc.sigint.core.MainKt"
else
    echo "❌ ÉCHEC DE LA COMPILATION. Vérifiez les sources."
fi
