#!/bin/bash
echo "[🛡️] INITIALISATION DU PROTOCOLE DE DURCISSEMENT..."

# 1. Génération d'un sel cryptographique unique
SALT=$(head /dev/urandom | tr -dc A-Za-z0-9 | head -c 16)
echo "[🎲] Signature polymorphe générée : $SALT"

# 2. Injection du sel dans le code source pour changer le Hash binaire
echo "// Polymorphic ID: $SALT" >> src/main/kotlin/com/fardc/sigint/core/Main.kt

# 3. Compilation avec les modules de sécurité avancée
CP="libs/javalin-5.6.3.jar:libs/slf4j-api-2.0.7.jar:libs/jakarta.servlet-api-5.0.0.jar"
kotlinc -cp "$CP" core/Security_Advanced/*.kt src/main/kotlin/com/fardc/sigint/core/*.kt -d build/classes

# 4. Création du JAR durci
jar cvf dist/SOVEREIGN-CORE-PSC-v5.2.jar -C build/classes . -C src/main/resources .

# 5. Vérification de l'empreinte (Preuve de polymorphisme)
SHA_RESULT=$(sha256sum dist/SOVEREIGN-CORE-PSC-v5.2.jar | awk '{print $1}')
echo "[✅] NOUVELLE EMPREINTE SHA-256 : $SHA_RESULT"
echo "[🛡️] BINAIRE INVIOLABLE PRÊT."
