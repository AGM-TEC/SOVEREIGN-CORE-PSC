# ---------------------------------------------------------
# ÉTAPE 1 : LA FORGE (BUILDER)
# ---------------------------------------------------------
FROM gradle:8.2-jdk17 AS builder

WORKDIR /workspace
COPY . .

# Forge du binaire avec limitation de mémoire pour éviter 
# le crash (OOM) sur des runners CI à ressources limitées.
RUN gradle wrapper --gradle-version 8.2 --distribution-type bin \
    && chmod +x gradlew \
    && ./gradlew clean shadowJar --no-daemon -Dorg.gradle.jvmargs="-Xmx512m"

# ---------------------------------------------------------
# ÉTAPE 2 : L'ENVELOPPE BLINDÉE (DISTROLESS / NON-ROOT)
# ---------------------------------------------------------
# Utilisation stricte d'une image sans terminal ni système d'exploitation.
FROM gcr.io/distroless/java17-debian12:nonroot

WORKDIR /app

# Récupération du binaire depuis la forge
COPY --from=builder /workspace/build/libs/sigint-core-all.jar /app/SovereignCore.jar

# Configuration de sécurité : L'application s'exécute avec l'utilisateur "nonroot" intégré.
USER nonroot

# Exécution stricte (Pas de shell sous-jacent)
ENTRYPOINT ["java", "-Xmx256m", "-jar", "/app/SovereignCore.jar"]
