#!/bin/bash
echo "[🕵️] OPÉRATION GHOST : Effacement des traces d'installation..."

# Effacement de l'historique du terminal
history -c
rm -rf ~/.bash_history

# Suppression des résidus de compilation Kotlin/Java
rm -rf ~/.gradle
rm -rf ~/.konan
rm -rf /data/data/com.termux/files/usr/tmp/*

# Sécurisation des permissions des logs
chmod 600 dashboard/*.log
chmod 600 core/audit/*.enc

echo "[✅] TRACES EFFACÉES. Le système opère désormais en mode fantôme."
