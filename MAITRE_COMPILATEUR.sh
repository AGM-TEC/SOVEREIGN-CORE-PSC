#!/bin/bash
# =============================================================
# PROJECT: SOVEREIGN-CORE-PSC v35.2
# MODULE: MAITRE COMPILATEUR (Final Build)
# ROLE: Fusion du Code et de l'Infrastructure
# =============================================================

echo "🔥 [FORGE] Démarrage de la compilation du Monolithe v35.2..."

# 1. Préparation de l'environnement
mkdir -p build/classes
mkdir -p build/bin

# 2. Compilation de tous les modules Java
echo "[⚙️] Compilation des modules : Crypto, Sigint, Decision, Monolith..."
javac -d build/classes src/main/java/sovereign/core/crypto/*.java                      src/main/java/sovereign/core/sigint/*.java                      src/main/java/sovereign/core/decision/*.java                      src/main/java/sovereign/core/monolith/*.java

if [ 0 -ne 0 ]; then
    echo "🚨 [ERREUR] Échec de la compilation. Vérifiez le code source."
    exit 1
fi

# 3. Création du Manifeste de lancement
echo "Main-Class: sovereign.core.monolith.SovereignC4ISRMonolith" > manifest.txt

# 4. Assemblage du Monolithe (Fat-Jar)
echo "[📦] Assemblage du binaire de combat..."
jar cfm build/bin/Sovereign_C4ISR_v35.1.jar manifest.txt -C build/classes .
rm manifest.txt

# 5. Scellage et Backup de Résilience
echo "[🔐] Scellage et création du backup d'auto-guérison..."
sha256sum build/bin/Sovereign_C4ISR_v35.1.jar > build/bin/CERTIFICAT_V35_1.txt
cp build/bin/Sovereign_C4ISR_v35.1.jar build/bin/.backup_Sovereign_v35.1

echo "------------------------------------------------------------"
echo "✅ FORGE RÉUSSIE : build/bin/Sovereign_C4ISR_v35.1.jar est prêt."
echo "🛡️ EMPREINTE : $(cat build/bin/CERTIFICAT_V35_1.txt | awk '{print $1}')"
echo "------------------------------------------------------------"
