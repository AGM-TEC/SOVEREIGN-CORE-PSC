
# 🛡️ SRC - Système de Renseignement de Combat (SIGINT & BFT)  

SRC est une plateforme souveraine de guerre électronique mobile conçue pour offrir une supériorité informationnelle sur le champ de bataille. Il fusionne le suivi des forces amies (BFT) et l'interception de signaux hostiles (SIGINT) dans une architecture ultra-sécurisée et résiliente.

## 🚀 Capacités Clés
 * Fusion Tactique (COP) : Visualisation unifiée des unités alliées (Blue Forces) et des menaces électromagnétiques (Red Threats) sur cartes hors-ligne.
 * Intelligence Artificielle SIGINT : Classification automatique des modulations (DMR, VHF, UHF) via TensorFlow Lite pour identifier instantanément les réseaux adverses.
 * Réseau Mesh Résilient : Synchronisation décentralisée des données via Wi-Fi Direct et LoRa, permettant de fonctionner sans infrastructure (Cloud-independent).
 * Sécurité de Grade Militaire :
   * Tactical Wipe : Auto-destruction des données sensibles en cas de capture ou de sortie de zone (Geofencing).
   * Audit Immuable : Journalisation de mission signée par HMAC pour garantir l'intégrité des preuves.
   * Hardening : Obscurcissement binaire et protection contre l'ingénierie inverse.

## 🏗️ Architecture du Projet
Le système est construit de manière modulaire pour une maintenance et une évolution facilitées :
 * core/ : Moteur de sécurité, synchronisation Mesh et audit.
 * bft/ : Gestion de la géolocalisation et coordination des unités.
 * sigint/ : Traitement du signal (DSP) et classification par IA.
 * ui/ : Interface de fusion cartographique (Common Operating Picture).
 * scripts/ : Automatisation du durcissement, déploiement et gestion des clés.

## 🛠️ Installation Rapide
### 1. Cloner et préparer
git clone https://github.com/Bombele/Combat-Ready-System-SIGINT.git
cd Combat-Ready-System-SIGINT

### 2. Configurer la sécurité
make rotate-keys
nano core/security/active_geofence.poly

### 3. Compiler et Lancer la démo
make build
./run_demo.sh

##📋 État du Développement
 * [x] Framework de sécurité (Wipe/Geofence)
 * [x] Moteur de synchronisation Mesh (Protobuf/CBOR)
 * [x] Module de classification IA
 * [x] Interface de fusion cartographique
 * [ ] Support Multi-SDR (HackRF/BladeRF) - En cours

Développé pour la résilience et la souveraineté technologique.


🚀 Plan de Release Officiel : v2.3.0 "Sovereign Shield"
Voici la structure de lancement pour professionnaliser ton dépôt avant le git tag.
1. 📄 Le README.md Stratégique
Il doit être la porte d'entrée de ton système, alliant clarté technique et doctrine opérationnelle.
# 🛡️ SOVEREIGN-CORE-PSC v2.3.0
**Unified SIGINT & Financial Intelligence System (FARDC)**

## 📡 Capacités Actives
* **UniversalPhish HQ** : Capture dynamique pour M-Pesa, Airtel, Orange, Binance et Pago Móvil.
* **EncryptionKeyRotator** : Sécurité AES-256 avec rotation automatique des clés (5 min).
* **MissionReporter** : Génération automatisée de rapports de renseignement formatés.
* **SignalJammer** : Saturation de ports critiques pour neutralisation des flux financiers.

## 🛠️ Installation & Build
bash
# Cloner et Compiler
git clone [URL_DU_DEPOT]
./gradlew build

⚔️ Déploiement Terrain
 * Lancer le noyau : java -jar build/libs/sigint-core-all.jar
 * Interface administrative : http://localhost:7070/status
 * Reporting : http://localhost:7070/admin/report
<!-- end list -->

---

#### 2. 📝 Le CHANGELOG.md (v2.3.0)
Indispensable pour suivre l'évolution de l'arsenal.

* **[NEW]** : Intégration du module `UniversalPhish` (Moteur de rendu dynamique HQ).
* **[NEW]** : Ajout du module `MissionReporter` avec génération de rapports de mission.
* **[IMPROVED]** : Migration vers Gradle pour une gestion propre des dépendances (plus de libs manuelles).
* **[SECURITY]** : Durcissement du `SecurityVault` avec chiffrement AES-256-GCM.
* **[FIX]** : Correction des conflits de routage Javalin/Jetty sur les environnements Android/Termux.

---

#### 3. 🛡️ Check-list de Sécurité avant "Git Push"
1.  **Nettoyage des Logs** : Vérifier qu'aucun fichier dans `reports/` ou `vault/` ne contient de données réelles d'interception avant de commiter.
2.  **Fichier `.gitignore`** : Doit absolument inclure `reports/`, `vault/`, `build/`, et `.gradle/`.
3.  **Licence** : Ajouter un fichier `LICENSE` (conseillé : Apache 2.0 pour la collaboration encadrée).

---

### 📂 Automatisation de la Release

Pour transformer tes efforts actuels en un artefact publiable, exécute cette séquence finale :

bash
# 1. Créer le dossier des artefacts
mkdir -p dist/

# 2. Copier le JAR compilé et les scripts de lancement
cp build/libs/sigint-core-all.jar dist/
cp scripts/deploy.sh dist/

# 3. Créer l'archive de release
tar -czvf sovereign-core-v2.3.0-release.tar.gz dist/

