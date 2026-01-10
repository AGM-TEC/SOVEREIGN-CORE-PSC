#!/bin/bash

# --- CONFIGURATION DU PONT TACTIQUE ---
PC_IP="192.168.1.XX" # À modifier selon l'IP de votre futur PC
PC_USER="commandant"
REMOTE_DIR="/home/commandant/SOVEREIGN_C2"
BINARY="./build/bin/SovereignCore-v8.jar"

echo "[🛰️] RECHERCHE DU POSTE DE COMMANDEMENT (PC)..."

# Test de présence du PC
ping -c 1 $PC_IP > /dev/null 2>&1

if [ $? -eq 0 ]; then
    echo "[✅] PC DÉTECTÉ. Amorçage du transfert sécurisé..."
    
    # Création du dossier distant et transfert via SCP (SSH)
    ssh $PC_USER@$PC_IP "mkdir -p $REMOTE_DIR"
    scp $BINARY $PC_USER@$PC_IP:$REMOTE_DIR/
    
    if [ $? -eq 0 ]; then
        echo "[💎] TRANSFERT RÉUSSI. Le SOVEREIGN CORE est maintenant sur votre station de travail."
        echo "[🛡️] LOCALISATION : $PC_IP:$REMOTE_DIR"
    else
        echo "[❌] ÉCHEC DU TRANSFERT. Vérifiez les accès SSH."
    fi
else
    echo "[⚠️] PC HORS-LIGNE. Le binaire reste sécurisé en local sur Termux."
fi
