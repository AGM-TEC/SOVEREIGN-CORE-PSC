#!/bin/bash
TARGET=""
for i in "$@"
do
case $i in
    --target=*)
    TARGET="${i#*=}"
    shift
    ;;
    *)
    echo "[ERREUR] Argument inconnu. Utilisez --target=RDC ou --target=VNZ"
    exit 1
    ;;
esac
done

if [ -z "$TARGET" ]; then
    echo "[ERREUR] Cible non définie."
    exit 1
fi

echo "[INIT] Démarrage de la forge pour la cible : $TARGET"

SRC_DIR="./src"
BUILD_DIR="./build"
LIB_DIR="./libs"
CORE_DIR="$SRC_DIR/core"
MODULES_DIR="$SRC_DIR/modules"
PAYLOAD_DIR="$BUILD_DIR/active_payload"

# 1. Préparation de l'environnement
rm -rf "$BUILD_DIR"
mkdir -p "$PAYLOAD_DIR"
mkdir -p "$LIB_DIR"

# 2. Résolution des Dépendances (ZeroMQ / JeroMQ)
JEROMQ_JAR="$LIB_DIR/jeromq-0.5.3.jar"
if [ ! -f "$JEROMQ_JAR" ]; then
    echo "[LOGISTIQUE] Téléchargement de l'armature réseau ZeroMQ (JeroMQ)..."
    curl -s -L -o "$JEROMQ_JAR" "https://repo1.maven.org/maven2/org/zeromq/jeromq/0.5.3/jeromq-0.5.3.jar"
    if [ $? -ne 0 ]; then
        echo "[ERREUR] Échec du téléchargement de ZeroMQ. Vérifiez votre connexion."
        exit 1
    fi
fi

# 3. Copie du Noyau
echo "[INFO] Injection du Noyau (Core)..."
cp -r "$CORE_DIR"/* "$PAYLOAD_DIR/" 2>/dev/null || :

# 4. Bifurcation Doctrinale
if [ "$TARGET" == "RDC" ]; then
    echo "[INFO] Chargement de la Doctrine : FARDC"
    cp -r "$MODULES_DIR/finint/MPesaCommander.kt" "$PAYLOAD_DIR/" 2>/dev/null || :
elif [ "$TARGET" == "VNZ" ]; then
    echo "[INFO] Chargement de la Doctrine : VENEZUELA"
    cp -r "$MODULES_DIR/finint/PagoMovilInterceptor.kt" "$PAYLOAD_DIR/" 2>/dev/null || :
fi

# 5. Compilation du Binaire Monolithique
echo "[INFO] Lancement du compilateur Kotlin (Silence des alertes Jansi)..."
SOURCES=$(find "$PAYLOAD_DIR" -name "*.kt")

if [ -z "$SOURCES" ]; then
    echo "[ERREUR] Aucun fichier source .kt trouvé."
    exit 1
fi

# Exécution avec le Classpath (-cp) pointant vers ZeroMQ
export JAVA_OPTS="-Dkotlin.colors.enabled=false"
kotlinc $SOURCES -cp "$JEROMQ_JAR" -include-runtime -d "$BUILD_DIR/SovereignCore-${TARGET}-v33.1.jar"

if [ $? -eq 0 ]; then
    echo "[SUCCÈS] Binaire forgé : $BUILD_DIR/SovereignCore-${TARGET}-v33.1.jar"
    echo "[SÉCURITÉ] Hash SHA-256 généré."
    sha256sum "$BUILD_DIR/SovereignCore-${TARGET}-v33.1.jar" > "$BUILD_DIR/signature.txt"
else
    echo "[ERREUR] Échec de la compilation."
    exit 1
fi
