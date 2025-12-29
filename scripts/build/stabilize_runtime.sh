#!/usr/bin/env bash
set -euo pipefail
echo "🔧 Réparation du pipeline SOVEREIGN-CORE-PSC..."

# 1. Nettoyage
rm -rf gradle gradlew gradlew.bat .gradle build/

# 2. Recréation du wrapper
gradle wrapper --gradle-version 8.2 --distribution-type bin
chmod +x gradlew
if command -v dos2unix >/dev/null 2>&1; then dos2unix gradlew || true; fi

# 3. Toolchain Java 17 (auto-provision)
cat > gradle.properties <<'EOF'
org.gradle.java.installations.auto-download=true
org.gradle.java.installations.enabled=true
EOF

# 4. settings.gradle.kts minimal (sans temurin)
echo 'rootProject.name = "SOVEREIGN-CORE-PSC"' > settings.gradle.kts

# 5. Build
./gradlew clean shadowJar --no-daemon

# 6. Vérification du JAR
JAR="build/libs/sigint-core-all.jar"
test -f "$JAR" || { echo "❌ JAR non généré"; exit 1; }
jar xf "$JAR" META-INF/MANIFEST.MF
grep -q "Main-Class: com.fardc.sigint.core.MainKt" META-INF/MANIFEST.MF || {
  echo "❌ Main-Class incorrect"; exit 1; }

# 7. Smoke test
java -jar "$JAR" || { echo "❌ Échec d'exécution"; exit 1; }
echo "✅ Pipeline opérationnel : $JAR"
