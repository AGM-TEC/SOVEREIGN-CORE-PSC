# 🛡️ SOVEREIGN-CORE-PSC v2.0
Système Intégré de Renseignement Technique et d'Action Offensive PSC Une plateforme de défense souveraine conçue pour la République Démocratique du Congo. Elle fusionne le renseignement électronique (SIGINT), le suivi tactique (BFT) et la supériorité numérique offensive (Cyber-Finance).
## 🛰️ Capacités du Noyau (Modules)
L'arsenal est composé de plusieurs unités de combat spécialisées, toutes orchestrées par le noyau central :
 * SIGINT & Interception : Surveillance et analyse des flux de données et communications financières (ISO8583).
 * Offensive Supprimacy : Scanner de vulnérabilités et pont offensif pour les opérations de pénétration.
 * Signal Stealth : Masquage de la signature réseau par fragmentation de paquets pour une discrétion totale.
 * Signal Jammer : Module de déni de service tactique par saturation de pile TCP.
 * Traffic Mirroring : Duplication discrète des flux réseau pour une surveillance passive bidirectionnelle.
 * Resilience & Auto-Heal : Surveillance système en temps réel avec capacité de réparation autonome.
## 🛠️ Installation Rapide (Termux / Linux)
Pour déployer l'arsenal sur un environnement mobile ou serveur, exécutez la séquence suivante :
### 1. Clonage du dépôt
git clone https://github.com/Bombele/SOVEREIGN-CORE-PSC.git
cd SOVEREIGN-CORE-PSC

### 2. Installation et configuration automatique
chmod +x install.sh
./install.sh

### 3. Lancement du noyau (v2.0-ULTIMATE)
java -Xmx512m -cp "build/libs/sigint-core-all.jar:libs/*" com.fardc.sigint.core.MainKt

## 📡 Interface de Commandement (C2)
Une fois activé, le noyau expose les points d'accès suivants pour le pilotage des opérations :
 * Dashboard d'état : http://localhost:7070/status
 * Contrôle Jammer : http://localhost:7070/jam/start?target=[IP]&port=[PORT]
 * Liaison Stealth : http://localhost:7070/stealth/connect?target=[IP]
 * Terminal Shell : Accès direct via le port sécurisé 9090.
## 🔐 Sécurité & Souveraineté
 * Chiffrement Militaire : Toutes les données sensibles sont protégées par un vault AES-256-GCM avec rotation dynamique des clés.
 * Contrôle d'Accès : Surveillance par le module Gatekeeper pour empêcher toute exécution non autorisée.
 * Kill-Switch : Procédure d'urgence pour la neutralisation immédiate et la purge de la mémoire du noyau.
## 📜 Licence
Ce projet est protégé par la Military License for ELINT/SIGINT Operations. Toute utilisation doit être conforme aux doctrines de défense de la souveraineté numérique.
Développé par Bombele | Release v2.0 - Full Tactical Mobile Edition.

