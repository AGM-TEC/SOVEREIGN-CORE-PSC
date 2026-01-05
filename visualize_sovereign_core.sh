#!/bin/bash
echo "================================================================"
echo "🛡️  SOVEREIGN-CORE-PSC v5.2 - ARCHITECTURE DE GRADE MILITAIRE"
echo "================================================================"
echo "Dossier Racine: $(pwd)"
echo ""

# Utilisation de 'find' pour simuler une arborescence propre
find . -maxdepth 4 -not -path '*/.*' | sed -e 's;[^/]*/;|____;g;s;____|; |;g'

echo ""
echo "----------------------------------------------------------------"
echo "📊 RÉSUMÉ DES COMPOSANTS CRITIQUES :"
echo "----------------------------------------------------------------"
echo "📍 Noyau Offensive : src/main/kotlin/.../ (Capture & Phishing)"
echo "🛡️  Bouclier Défensif : core/Security_Advanced/ (Anti-Dump, Wipe, SSL)"
echo "📡 Canal de Secours : core/Security_Advanced/MeshSyncEngine.kt"
echo "🎭 Camouflage : harden_binary.sh & process_hider.sh"
echo "💎 Rareté SIGINT : core/Security_Advanced/PacketSniffer.kt"
echo "📜 Manuel Opérationnel : SOVEREIGN-OPS-MANUAL.md"
echo "================================================================"
