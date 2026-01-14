#!/bin/bash
# ROLE: Verrouillage des ports pour la démonstration
echo "🛡️ [HARDENING] Verrouillage des ports non-souverains..."

# Autoriser uniquement le trafic interne et les ports C4ISR
iptables -A INPUT -i lo -j ACCEPT
iptables -A INPUT -p tcp --dport 8080 -j ACCEPT # API Monolithe
iptables -A INPUT -p tcp --dport 9001 -j ACCEPT # Flux SIGINT
iptables -A INPUT -j DROP # Bloquer tout le reste

echo "✅ Réseau blindé (Zero-Trust Network)."
