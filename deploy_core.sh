#!/bin/bash
# 🛡️ SOVEREIGN CORE v4.2 - FAST DEPLOYMENT SCRIPT

echo "--------------------------------------------------"
echo "🚀 INITIALISATION DU DÉPLOIEMENT : SOVEREIGN CORE"
echo "--------------------------------------------------"

# 1. Mise à jour du système Termux
echo "📦 Mise à jour des paquets..."
pkg update -y && pkg upgrade -y

# 2. Installation des dépendances critiques
echo "⚙️ Installation de Java 21, Git, Python et LibUSB..."
pkg install -y openjdk-17 git python libusb libandroid-glob

# 3. Configuration des outils SDR
echo "📡 Configuration des drivers RTL-SDR..."
pkg install -y rtl-sdr

# 4. Création des alias tactiques (Raccourcis)
echo "⌨️ Configuration des alias de commande..."
echo "alias core-start='java -Dorg.fusesource.jansi.Ansi.disable=true -cp \"dist/bin/SovereignCore.jar:dist/libs/*\" com.fardc.sigint.core.MainKt'" >> ~/.bashrc
echo "alias core-logs='tail -f logs/mission_data.json'" >> ~/.bashrc
echo "alias core-wipe='sh scripts/finalize_system.sh --wipe'" >> ~/.bashrc

# 5. Préparation des répertoires de mission
echo "📁 Création des structures de données..."
mkdir -p logs/audio
mkdir -p dist/bin
mkdir -p dist/libs

# 6. Attribution des permissions
echo "🔐 Sécurisation des scripts..."
chmod +x scripts/*.sh

echo "--------------------------------------------------"
echo "✅ DÉPLOIEMENT TERMINÉ AVEC SUCCÈS"
echo "💡 Tapez 'source ~/.bashrc' puis 'core-start' pour lancer."
echo "--------------------------------------------------"
