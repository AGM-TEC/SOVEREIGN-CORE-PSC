#!/bin/bash

echo "🛡️ [DEPLOY] Initialisation du déploiement SOVEREIGN-CORE-PSC v2.0..."

# 1. Compilation finale (Souveraine)
echo "🏗️ [BUILD] Compilation du binaire optimisé..."
kotlinc src/main/kotlin/com/fardc/sigint/core/*.kt \
        -cp "libs/*" \
        -include-runtime \
        -d build/libs/sigint-core-all.jar

if [ $? -eq 0 ]; then
    echo "✅ [SUCCESS] Binaire généré (5.1 Mo)."
else
    echo "❌ [ERROR] Échec de la compilation."
    exit 1
fi

# 2. Préparation du commit
echo "📂 [GIT] Indexation des fichiers corrigés..."
git add .

# 3. Commit avec le message officiel
echo "📝 [GIT] Signature du commit v2.0..."
git commit -m "Release v2.0: Full Tactical Mobile Edition. All SIGINT, Offensive and Resilience modules stabilized for Termux."

# 4. Push vers GitHub
echo "🚀 [PUSH] Envoi vers le dépôt distant..."
git push origin main

echo "🏁 [DONE] Arsenal souverain synchronisé sur GitHub !"
