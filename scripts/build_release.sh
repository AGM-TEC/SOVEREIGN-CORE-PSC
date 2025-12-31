#!/bin/bash
echo "🛡️ SOVEREIGN-CORE-PSC | Préparation de la Release v2.3.0"
echo "------------------------------------------------------"

# 1. Dossiers de sortie
mkdir -p build/libs
mkdir -p dist/

# 2. Construction MANUELLE du Classpath (Indispensable pour Termux)
# On liste chaque JAR un par un
CP="libs/javalin-5.6.3.jar:libs/slf4j-api-2.0.7.jar:libs/slf4j-simple-2.0.7.jar:libs/jackson-databind-2.15.2.jar:libs/jackson-core-2.15.2.jar:libs/jackson-annotations-2.15.2.jar:libs/jetty-server-11.0.15.jar:libs/jetty-util-11.0.15.jar:libs/jetty-http-11.0.15.jar:libs/jetty-io-11.0.15.jar"

echo "⚙️ Compilation des modules Kotlin..."
# Utilisation de la variable CP
kotlinc src/main/kotlin/com/fardc/sigint/core/*.kt \
-cp "$CP" \
-include-runtime -d build/libs/sigint-core-all.jar

if [ $? -eq 0 ]; then
    echo "✅ COMPILATION RÉUSSIE."
    cp build/libs/sigint-core-all.jar dist/
    echo "🚀 RELEASE GÉNÉRÉE : dist/sigint-core-all.jar"
else
    echo "❌ ÉCHEC DE LA COMPILATION. Vérifiez les chemins."
    exit 1
fi
