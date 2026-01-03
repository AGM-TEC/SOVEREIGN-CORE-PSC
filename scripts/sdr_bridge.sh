#!/bin/bash
echo "📡 SDR BRIDGE: Initialisation du pilote RTL-SDR..."
# Utilisation de rtl_power pour scanner les fréquences hostiles
rtl_power -f 2.4G:2.5G:1M -g 40 -i 1s logs/spectrum_raw.csv &
echo "✅ SDR BRIDGE: Flux de données dirigé vers le Core."
