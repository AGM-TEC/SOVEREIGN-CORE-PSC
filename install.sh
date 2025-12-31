#!/bin/bash

echo "🛡️ [INSTALL] Initialisation de l'arsenal SOVEREIGN-CORE-PSC v2.0..."

# 1. Mise à jour des dépendances système
echo "📦 [SYS] Mise à jour des packages..."
pkg update -y && pkg upgrade -y
pkg install -y git openjdk-17 kotlin nmap coreutils

# 2. Vérification des répertoires
echo "📁 [DIR] Configuration de l'espace de travail..."
mkdir -p build/libs
mkdir -p libs

# 3. Compilation du noyau
echo "🏗️ [BUILD] Compilation du binaire souverain..."
kotlinc src/main/kotlin/com/fardc/sigint/core/*.kt \
        -cp "libs/*" \
        -include-runtime \
        -d build/libs/sigint-core-all.jar

if [ $? -eq 0 ]; then
    echo "✅ [SUCCESS] Binaire généré avec succès."
else
    echo "❌ [ERROR] Échec de la compilation. Vérifiez le code source."
    exit 1
fi

# 4. Finalisation
echo "🚀 [READY] Installation terminée."
echo "-------------------------------------------------------"
echo "Pour lancer le noyau, utilisez la commande suivante :"
echo "java -Xmx512m -cp 'build/libs/sigint-core-all.jar:libs/*' com.fardc.sigint.core.MainKt"
echo "-------------------------------------------------------"
