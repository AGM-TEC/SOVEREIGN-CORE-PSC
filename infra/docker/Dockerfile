# 1. BASE: Utilisation d'un environnement JRE épuré (Standard Militaire)
FROM eclipse-temurin:17-jre-alpine

# 2. MÉTADONNÉES DE SOUVERAINETÉ
LABEL project="SOVEREIGN-CORE-PSC"
LABEL version="35.2"
LABEL security="ZERO-TRUST"

# 3. ENVIRONNEMENT HARDWARE-READY
WORKDIR /app
RUN apk add --no-cache libstdc++ gcompat # Nécessaire pour les calculs cryptographiques lourds

# 4. INJECTION DU MONOLITHE ET DE L'ARMURE
COPY build/bin/Sovereign_C4ISR_v35.1.jar /app/monolith.jar
COPY build/bin/CERTIFICAT_V35_1.txt /app/cert.txt
COPY infra/ /app/infra/

# 5. SÉCURITÉ : Exécution en mode non-root (Anti-Privilege Escalation)
RUN addgroup -S sovereign && adduser -S gaby -G sovereign
USER gaby

# 6. LANCEMENT VIA LE VERROU DE SOUVERAINETÉ
ENTRYPOINT ["/bin/sh", "-c", "./infra/cosign/verify_signature.sh"]
