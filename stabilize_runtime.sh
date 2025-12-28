#!/usr/bin/env bash
set -euo pipefail

echo "🛡️ Stabilisation SOVEREIGN-CORE-PSC (runtime + build)..."

# 1) Assainir gradlew (CRLF → LF) et permissions
if command -v dos2unix >/dev/null 2>&1; then
  dos2unix gradlew || true
fi
chmod +x gradlew || true

# 2) Verrouiller JAVA_HOME (Java 17)
if [ -z "${JAVA_HOME:-}" ]; then
  if [ -d "/usr/lib/jvm/java-17-openjdk-amd64" ]; then
    export JAVA_HOME="/usr/lib/jvm/java-17-openjdk-amd64"
  fi
fi
export PATH="$JAVA_HOME/bin:$PATH"
java -version || { echo "❌ JDK 17 introuvable"; exit 1; }

# 3) Verrouiller le wrapper (Gradle 8.2)
rm -rf .gradle
./gradlew -v >/dev/null 2>&1 || {
  echo "[*] Régénération du wrapper 8.2..."
  gradle wrapper --gradle-version 8.2 --distribution-type bin
  chmod +x gradlew
}
./gradlew -v | grep "Gradle 8.2" >/dev/null || {
  echo "❌ Wrapper non verrouillé à 8.2"; exit 1;
}

# 4) Build propre avec ShadowJar
./gradlew clean shadowJar --no-daemon

# 5) Vérification JAR
JAR="build/libs/sigint-core-all.jar"
if [ -f "$JAR" ]; then
  echo "✅ JAR détecté : $JAR"
else
  echo "❌ JAR non généré"; exit 1
fi

# 6) Lancement de smoke-test (sans opérations réseau)
echo "🚀 Smoke test (stdout uniquement)"
java -jar "$JAR" || { echo "❌ Échec d’exécution"; exit 1; }

echo "🎯 Stabilisation réussie."