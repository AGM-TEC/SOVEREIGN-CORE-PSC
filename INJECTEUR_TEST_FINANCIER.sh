#!/bin/bash
# MODULE: TRAFFIC GENERATOR (SIMULATION TRANSFERT)
# ROLE: Envoyer un paquet contenant l'ID du suspect pour tester le déroutement

echo -e "\e[1;34m📤 [TEST] Envoi d'une transaction fictive vers le suspect...\e[0m"
echo "Contenu : TRANSFERT 1000 USD TO 0812345678"

# On utilise 'nc' (netcat) pour envoyer un paquet UDP sur le port 9999
echo "TRANSFERT 1000 USD TO 0812345678" | nc -u -w1 127.0.0.1 9999

echo -e "\e[1;32m✅ Paquet envoyé sur l'interface locale.\e[0m"
