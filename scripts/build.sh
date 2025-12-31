cat << 'EOF' > scripts/build.sh
#!/bin/bash
# SOVEREIGN-CORE-PSC | Professional Build System
# Version: 2.3.0

# Couleurs pour le terminal
GREEN='\033[0;32m'
RED='\033[0;31m'
NC='\033[0m'

echo -e "${GREEN}🛡️  DÉMARRAGE DU BUILD - SOVEREIGN CORE v2.3.0${NC}"

# 1. Nettoyage
rm -rf build/
mkdir -p build/libs

# 2. Compilation
echo "⚙️  Compilation des sources Kotlin..."
kotlinc src/main/kotlin/com/fardc/sigint/core/*.kt \
-cp "libs/*" \
-include-runtime -d build/libs/sigint-core-all.jar

if [ $? -eq 0 ]; then
    echo -e "${GREEN}✅ COMPILATION RÉUSSIE${NC}"
    echo -e "📦 Artefact généré : ${GREEN}build/libs/sigint-core-all.jar${NC}"
else
    echo -e "${RED}❌ ÉCHEC DU BUILD. Vérifiez les erreurs ci-dessus.${NC}"
    exit 1
fi
EOF
chmod +x scripts/build.sh
