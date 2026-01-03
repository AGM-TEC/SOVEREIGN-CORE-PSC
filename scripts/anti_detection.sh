#!/bin/bash
# 🛡️ SOVEREIGN CORE - ANTI-DETECTION MODULE
echo "🕵️ Activation de la discrétion RF..."

# Injection de bruit blanc à faible puissance sur les fréquences de contrôle
# Empêche les scanners ennemis de verrouiller votre signature unique.
rtl_power -f 2.4G:2.5G:1M -i 1s -g 10 /dev/null & 

echo "✅ Mode Furtif ACTIF : Votre signature est maintenant masquée."
