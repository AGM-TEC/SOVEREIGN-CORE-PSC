cat << 'EOF' > scripts/setup.sh
#!/bin/bash
echo "📡 SOVEREIGN-CORE | Récupération des dépendances SIGINT..."
mkdir -p libs

# Liste des JARs essentiels (Maven Central)
BASE_URL="https://repo1.maven.org/maven2"

declare -A LIBS=(
    ["javalin-5.6.3.jar"]="$BASE_URL/io/javalin/javalin/5.6.3/javalin-5.6.3.jar"
    ["slf4j-api-2.0.7.jar"]="$BASE_URL/org/slf4j/slf4j-api/2.0.7/slf4j-api-2.0.7.jar"
    ["slf4j-simple-2.0.7.jar"]="$BASE_URL/org/slf4j/slf4j-simple/2.0.7/slf4j-simple-2.0.7.jar"
    ["jackson-databind-2.15.2.jar"]="$BASE_URL/com/fasterxml/jackson/core/jackson-databind/2.15.2/jackson-databind-2.15.2.jar"
    ["jackson-core-2.15.2.jar"]="$BASE_URL/com/fasterxml/jackson/core/jackson-core/2.15.2/jackson-core-2.15.2.jar"
    ["jackson-annotations-2.15.2.jar"]="$BASE_URL/com/fasterxml/jackson/core/jackson-annotations/2.15.2/jackson-annotations-2.15.2.jar"
    ["jetty-server-11.0.15.jar"]="$BASE_URL/org/eclipse/jetty/jetty-server/11.0.15/jetty-server-11.0.15.jar"
    ["jetty-util-11.0.15.jar"]="$BASE_URL/org/eclipse/jetty/jetty-util/11.0.15/jetty-util-11.0.15.jar"
    ["jetty-http-11.0.15.jar"]="$BASE_URL/org/eclipse/jetty/jetty-http/11.0.15/jetty-http-11.0.15.jar"
    ["jetty-io-11.0.15.jar"]="$BASE_URL/org/eclipse/jetty/jetty-io/11.0.15/jetty-io-11.0.15.jar"
)

for lib in "${!LIBS[@]}"; do
    if [ ! -f "libs/$lib" ]; then
        echo "⬇️ Téléchargement de $lib..."
        curl -L "${LIBS[$lib]}" -o "libs/$lib"
    else
        echo "✅ $lib est déjà présent."
    fi
done

echo "⚙️  Configuration terminée. Vous pouvez lancer ./scripts/build_release.sh"
EOF
chmod +x scripts/setup.sh
