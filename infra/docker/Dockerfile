FROM gradle:8.2-jdk17 AS builder

# Préparer l'environnement
WORKDIR /workspace
COPY . .

# Vérification du wrapper et build
RUN gradle wrapper --gradle-version 8.2 --distribution-type bin \
    && chmod +x gradlew \
    && ./gradlew clean shadowJar --no-daemon

# Image finale (plus légère)
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=builder /workspace/build/libs/sigint-core-all.jar /app/sigint-core-all.jar

ENTRYPOINT ["java", "-jar", "sigint-core-all.jar"]
