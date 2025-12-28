#!/usr/bin/env bash
set -euo pipefail

echo "🛡️ Stabilisation SOVEREIGN-CORE-PSC..."

# 1) Java 17
if [ -d "/usr/lib/jvm/java-17-openjdk-amd64" ]; then
  export JAVA_HOME="/usr/lib/jvm/java-17-openjdk-amd64"
elif [ -n "${JAVA_HOME_17_X64:-}" ]; then
  export JAVA_HOME="$JAVA_HOME_17_X64"
else
  echo "❌ JDK 17 introuvable"; exit 1
fi
export PATH="$JAVA_HOME/bin:$PATH"
java -version || { echo "❌ JDK non opérationnel"; exit 1; }

# 2) Wrapper
rm -rf gradle gradlew gradlew.bat .gradle build/
gradle wrapper --gradle-version 8.2 --distribution-type bin
if command -v dos2unix >/dev/null 2>&1; then dos2unix gradlew || true; fi
chmod +x gradlew
./gradlew -v | grep "Gradle 8.2" >/dev/null || { echo "❌ Wrapper non verrouillé"; exit 1; }

# 3) Build
./gradlew clean shadowJar --no-daemon

# 4) JAR
JAR=build/libs/sigint-core-all.jar
test -f "$JAR" || { echo "❌ JAR non généré"; exit 1; }

# 5) Smoke test
java -jar "$JAR" || { echo "❌ Exécution échouée"; exit 1; }

echo "🎯 Stabilisation réussie : $JAR"