#!/usr/bin/env bash
set -euo pipefail

echo "🛡️ Protocole souverain: purge, lock, build, vérif, déploiement"

# 0) Préconditions
REPO_ROOT="$(pwd)"
JAR="build/libs/sigint-core-all.jar"
RELEASE_DIR="release/signed"
MAIN_CLASS="com.fardc.sigint.core.MainKt"

# 1) Purge radicale des artefacts et verrous locaux
rm -rf "$REPO_ROOT/gradle" "$REPO_ROOT/gradlew" "$REPO_ROOT/gradlew.bat" "$REPO_ROOT/.gradle" "$REPO_ROOT/build" "$REPO_ROOT/$RELEASE_DIR"

# 2) Régénération du wrapper Gradle (via Gradle global de Codespaces)
echo "🔧 Régénération du wrapper Gradle 8.2"
gradle wrapper --gradle-version 8.2 --distribution-type bin
chmod +x gradlew
command -v dos2unix >/dev/null 2>&1 && dos2unix gradlew || true

# 3) Provision toolchain Java 17 via Gradle (auto-download)
cat > settings.gradle.kts <<'EOF'
rootProject.name = "SOVEREIGN-CORE-PSC"
EOF

cat > gradle.properties <<'EOF'
org.gradle.java.installations.auto-download=true
org.gradle.java.installations.enabled=true
EOF

# 4) Build reproductible (ShadowJar)
echo "🧱 Build (clean + shadowJar)"
./gradlew clean shadowJar --no-daemon

# 5) Vérifications du JAR + manifest
echo "🔍 Vérification du binaire"
test -f "$JAR" || { echo "❌ JAR non généré"; exit 1; }
jar xf "$JAR" META-INF/MANIFEST.MF
grep -q "Main-Class: ${MAIN_CLASS}" META-INF/MANIFEST.MF || {
  echo "❌ Main-Class incorrect dans le manifest"; exit 1; }

# 6) Smoke test (stdout uniquement)
echo "🚀 Smoke test"
java -jar "$JAR" || { echo "❌ Exécution échouée"; exit 1; }

# 7) Déploiement souverain signé (horodatage)
echo "📦 Déploiement"
mkdir -p "$RELEASE_DIR"
STAMP="$(date +%Y%m%d-%H%M%S)"
cp "$JAR" "$RELEASE_DIR/sigint-core-${STAMP}.jar"

# 8) Empreinte et manifeste de release
sha256sum "$RELEASE_DIR/sigint-core-${STAMP}.jar" | tee "$RELEASE_DIR/sigint-core-${STAMP}.sha256"
cat > "$RELEASE_DIR/manifest-release-${STAMP}.txt" <<EOF
Artifact: sigint-core-${STAMP}.jar
Main-Class: ${MAIN_CLASS}
SHA256: $(sha256sum "$RELEASE_DIR/sigint-core-${STAMP}.jar" | awk '{print $1}')
Built-With: Gradle 8.2 + Kotlin JVM
Toolchain: Java 17 (auto-provision)
EOF

echo "✅ Déploiement prêt: $RELEASE_DIR/sigint-core-${STAMP}.jar"
