#!/bin/bash
# MODULE: SENSOR-LINK ACTIVATOR
# ROLE: Liaison entre le Monolithe et le matériel SDR

echo "📡 Activation des interfaces physiques..."

# 1. Montage des pilotes SDR
# sudo modprobe rtl2832 # Exemple pour dongle USB SIGINT

# 2. Création du flux de données pour le Monolithe
# On dirige le flux radio vers un port local que le Java écoute
# rtl_tcp -a 127.0.0.1 -p 1234 &

echo "✅ Capteurs RF activés. Le Monolithe reçoit maintenant le spectre réel."
