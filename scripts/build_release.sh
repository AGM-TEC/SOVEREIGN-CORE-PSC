cat << 'EOF' > scripts/build_release.sh
#!/bin/bash
echo "🛡️ SOVEREIGN-CORE-PSC | Préparation de la Release v2.3.0"
echo "------------------------------------------------------"

# 1. Création des dossiers
mkdir -p build/libs
mkdir -p dist/

# 2. Définition du Classpath explicite (Vital pour Termux)
CP="libs/javalin-5.6.3.jar:libs/slf4j-api-2.0.7.jar:libs/jackson-databind-2.15.2.jar:libs/jackson-core-2.15.2.jar:libs/jackson-annotations-2.15.2.jar:libs/jetty-server-11.0.15.jar:libs/jetty-util-11.0.15.jar:libs/jetty-http-11.0.15.jar:libs/jetty-io-11.0.15.jar"

echo "⚙️ Compilation des modules (Main, Reporter, Phish)..."
kotlinc src/main/kotlin/com/fardc/sigint/core/*.kt \
-cp "$CP" \
-include-runtime -d build/libs/sigint-core-all.jar

if [ $? -eq 0 ]; then
    echo "✅ COMPILATION RÉUSSIE."
    
    echo "📦 Packaging de la release..."
    cp build/libs/sigint-core-all.jar dist/
    
    echo "------------------------------------------------------"
    echo "🚀 RELEASE PRÊTE : dist/sigint-core-all.jar"
    echo "LANCEMENT : java -cp 'dist/sigint-core-all.jar:libs/*' com.fardc.sigint.core.MainKt"
else
    echo "❌ ÉCHEC DE LA COMPILATION. Vérifiez si les JARs sont présents dans le dossier libs/."
fi
EOF

chmod +x scripts/build_release.sh
