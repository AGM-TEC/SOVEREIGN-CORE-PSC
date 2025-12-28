#!/usr/bin/env bash
set -euo pipefail

echo "🛡️ Stabilisation SOVEREIGN-CORE-PSC..."

# 1) Nettoyage
rm -rf gradle gradlew gradlew.bat .gradle build/

# 2) Wrapper de transition (utilise gradle global si présent)
gradle wrapper --gradle-version 9.2.1 --distribution-type bin || true
# Permissions + fins de ligne
if command -v dos2unix >/dev/null 2>&1; then dos2unix gradlew || true; fi
chmod +x gradlew

# 3) Verrouillage wrapper 8.2 (stabilité)
./gradlew wrapper --gradle-version 8.2 --distribution-type bin
chmod +x gradlew

# 4) Configuration toolchain Java 17 (auto-provision)
# Assure le téléchargement du JDK 17 Temurin par Gradle
cat > settings.gradle.kts <<'EOF'
rootProject.name = "SOVEREIGN-CORE-PSC"

toolchainManagement {
  jvm {
    javaRepositories {
      repository("temurin") {
        temurin()
      }
    }
  }
}
EOF

# Propriétés Gradle (auto-download)
cat > gradle.properties <<'EOF'
org.gradle.java.installations.auto-download=true
org.gradle.java.installations.enabled=true
EOF

# 5) Build shadow
./gradlew clean shadowJar --no-daemon

# 6) Vérifications JAR + manifest
JAR="build/libs/sigint-core-all.jar"
test -f "$JAR" || { echo "❌ JAR non généré"; exit 1; }

jar xf "$JAR" META-INF/MANIFEST.MF
grep -q "Main-Class: com.fardc.sigint.core.MainKt" META-INF/MANIFEST.MF || {
  echo "❌ Main-Class incorrect dans le JAR"; exit 1; }

# 7) Smoke test
java -jar "$JAR" || { echo "❌ Exécution échouée"; exit 1; }

echo "✅ Stabilisation réussie : $JAR"