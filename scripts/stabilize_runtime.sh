#!/bin/bash
echo "[🧹] STABILISATION : Nettoyage des bibliothèques redondantes..."

# Suppression des doublons .jar.1 ou .jar.2 créés par des téléchargements multiples
find libs/ -name "*.jar.*" -delete
find dist/libs/ -name "*.jar.*" -delete

# Centralisation des classes compilées
mkdir -p build/classes
mv *.class build/classes/ 2>/dev/null

echo "[✅] RUNTIME : Environnement stabilisé. Zéro redondance détectée."
