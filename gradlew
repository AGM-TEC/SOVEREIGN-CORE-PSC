#!/usr/bin/env bash

# --- CONFIGURATION PSC ---
# Force la version 8.2 pour éviter l'erreur d'incompatibilité Gradle 9.2
GRADLE_VERSION="8.2"
GRADLE_DIST="bin"

echo "📡 Initialisation du moteur de build SOVEREIGN-CORE..."

# --- VÉRIFICATION JAVA 17 ---
# Prévient l'empoisonnement par Java 25 constaté dans Codespaces
if [[ $(java -version 2>&1) != *"17"* ]]; then
    export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
    export PATH=$JAVA_HOME/bin:$PATH
fi

# --- AUTO-BOOTSTRAP ---
# Si le wrapper officiel est absent, on utilise le gradle système pour le générer
if [ ! -d "gradle/wrapper" ]; then
    echo "🏗️ Reconstruction du socle Gradle $GRADLE_VERSION..."
    gradle wrapper --gradle-version $GRADLE_VERSION --distribution-type $GRADLE_DIST
fi

# --- EXÉCUTION DU COMMANDEMENT ---
# On lance la compilation avec les options de mémoire optimisées pour Codespaces
exec ./gradle/wrapper/gradle-wrapper.jar "$@"
