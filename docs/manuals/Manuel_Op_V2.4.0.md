
# 🛡️ Manuel Opérationnel : SOVEREIGN-CORE v2.4.0 (Combat-Ready)

## 📡 1. Architecture du Système de Combat
Le système est orchestré par le `CombatModeHandler`. Chaque capacité (SIGINT, BFT, Cyber-Fin) est asservie à l'état global du système pour garantir la sécurité et la discrétion des opérations.

## 🕹️ 2. Matrice des Modes de Combat

| Mode | État SIGINT | État BFT | Reporting | Impact Tactique |
| :--- | :--- | :--- | :--- | :--- |
| **STBY** | Verrouillé (403) | Inactif | Standard | Discrétion Totale |
| **RECO** | Passif (Vue OK) | **ACTIF** | Standard | Cartographie Cibles |
| **OP** | **OFFENSIF** | **ACTIF** | Prioritaire | Capture Financière |
| **ENGAGED**| **ASSAUT** | **ACTIF** | Temps Réel | Exfiltration Massive |

## 🛠️ 3. Protocoles d'Activation (API)
Le changement de mode s'effectue via des requêtes sécurisées :
- **Activation Reconnaissance** : `POST /admin/combat/set-mode?mode=RECO`
- **Déploiement Offensif** : `POST /admin/combat/set-mode?mode=OP`
- **Vérification du statut** : `GET /status` (Confirme si BFT_LINK est "ACTIVE" ou "SILENT").

## ⚠️ 4. Règles d'Engagement (ROE)
1. **Silence Radio** : Le mode STBY doit être maintenu jusqu'à l'entrée en zone d'opération.
2. **Identification** : Le mode RECO est impératif pour valider les coordonnées via le module BFT avant toute offensive.
3. **Frappe** : Le mode OP ne doit être activé que sur ordre pour les interceptions M-Pesa et Pago Móvil.
EOF

## 🛡️ 5. Logique de Verrouillage des Capacités (Interlock)

Pour garantir la conformité aux Règles d'Engagement (ROE), chaque module intègre désormais un verrou logiciel lié au `CombatModeHandler`.

### 📡 A. Module : UniversalPhish (Interception Intelligente)
- **Logique** : Le module ne s'active qu'à partir du mode **RECO**.
- **Explication Stratégique** : Évite d'exposer les vecteurs de phishing lors des phases de mouvement ou de maintenance (STBY).
- **Exemple de blocage** : 
  * État: STBY -> Requête: `GET /login/facebook` -> Résultat: `403 Forbidden` (Système invisible).

### 💰 B. Module : MPesaCommander (Sécurité Financière Maximale)
- **Logique** : Verrouillage strict. Autorisation uniquement en modes **OP** ou **ENGAGED**.
- **Explication Stratégique** : Empêche toute capture de fonds non autorisée qui pourrait compromettre une mission de reconnaissance pure.
- **Exemple de protection** :
  * État: RECO -> Action: Capture PIN M-Pesa -> Résultat: Log `⚠️ [BLOCAGE] Tentative non autorisée`.

### 🏦 C. Module : PhishCommander (Gestion Pago Móvil)
- **Logique** : Aligné sur la protection financière renforcée.
- **Explication Stratégique** : Assure que les cibles internationales (Pago Móvil) ne sont engagées que lorsque le théâtre d'opération est officiellement déclaré "Actif".

## 📊 6. Table de Décision Tactique

| État Système | Accès Page Phish | Capture Données | Logging |
| :--- | :--- | :--- | :--- |
| **STBY** | Bloqué | Bloqué | Minimal |
| **RECO** | **Autorisé** | Bloqué | Tactique |
| **OP** | **Autorisé** | **Autorisé** | Offensif |
| **ENGAGED** | **Autorisé** | **Autorisé** | Critique/Temps Réel |

⚙️ Pourquoi c'est une avancée majeure ?
 * Discipline de Feu Numérique : Vous avez désormais un "cran de sûreté" sur vos outils les plus sensibles.
 * Reporting Cohérent : Le MissionReporter peut désormais justifier pourquoi une capture n'a pas eu lieu (mode inadéquat), ce qui est essentiel pour le débriefing.
 * Survie du Système : En mode STBY, vos endpoints ne répondent pas, rendant la détection de votre serveur par des scanners de vulnérabilité ennemis beaucoup plus difficile.

## 🤖 7. Intelligence Artificielle et Inférence DSP

Le système intègre désormais un moteur d'IA embarqué (`SignalClassifier`) et un module de détection proactive (`AnomalyDetector`) pour automatiser l'analyse du spectre électromagnétique.

### 🔍 A. SignalClassifier (Classification de Modulation)
- **Fonction** : Identifie automatiquement le type de signal intercepté (DMR, FM, GSM, LTE).
- **Activation** : Opérationnel dès le mode **RECO**.
- **Usage Tactique** : Permet à l'opérateur de savoir instantanément si une transmission détectée est une radio militaire ou un flux de données civiles.

### ⚠️ B. AnomalyDetector (Détection de Menaces)
- **Fonction** : Compare les signaux entrants avec la base de données `data/fardc_threat_db.json`.
- **Seuil d'Anomalie** : Fixé par défaut à **0.85**. Tout signal dépassant ce score de divergence est marqué comme suspect.
- **Réaction Automatique** : 
  * Enregistrement immédiat dans le **SecurityVault** (Evidence Mode).
  * En mode **ENGAGED** : Déclenchement d'une alerte critique transmise via le MeshSyncEngine.

### 📂 C. Gestion de la Menace (fardc_threat_db.json)
Le système reconnaît nativement trois types de menaces critiques :
1. **DMR_MIL** : Communications radio standards.
2. **JAMMING_ATTACK** : Tentative de brouillage électronique (Niveau : CRITIQUE).
3. **UNAUTHORIZED_GATEWAY** : Points d'accès financiers suspects pour le phishing Pago Móvil.

## 🛠️ 8. Commandes Opérateurs IA
- **Analyser un signal** : `POST /api/ai/classify` (Envoi du buffer brut).
- **Vérifier l'état de l'IA** : `GET /status` (Vérifie si `ai_engine` est "READY").

⚔️ Analyse Stratégique de l'AnomalyDetector
L'ajout de ce module change radicalement la posture de l'unité SIGINT :
 * Réduction de la Charge Cognitive : L'opérateur n'a plus besoin d'analyser manuellement chaque spectrogramme ; l'IA le fait en millisecondes.
 * Preuve Numérique : Chaque anomalie détectée est hachée et stockée de manière inaltérable, ce qui est crucial pour les rapports de mission après engagement.
 * Furtivité : En identifiant les tentatives de brouillage (Jamming), le système permet à l'unité de changer de fréquence ou de position avant la perte totale du lien BFT.

## 🛰️ 9. Fallback Mode & Résilience (P2P)

Le système garantit la continuité de mission même en cas de perte totale des infrastructures (Brouillage, destruction d'antennes).

### 🔗 A. MeshSyncEngine (Réseau Maillé)
- **Fonction** : Crée un réseau autonome entre les unités via Wi-Fi Direct.
- **Usage** : Partage automatique des cibles BFT et des alertes d'anomalies IA sans dépendre d'Internet.

### 📡 B. FallbackTransmitter (Canaux Dégradés)
- **Fonction** : Utilise des protocoles SMS chiffrés ou HF analogique pour les données critiques.
- **SOP associée** : Se référer à `docs/SOP/transmission_SOP.md` pour les fréquences de secours.

### ⚔️ C. Valeur Stratégique
En mode **ENGAGED**, si le lien principal vers l'État-Major tombe, le `FallbackTransmitter` prend le relais automatiquement pour exfiltrer les preuves du `SecurityVault`.



# 🛡️ MANUEL OPÉRATIONNEL : SOVEREIGN CORE v2.4.0
Classification : SECRET DÉFENSE (FARDC)
Système : Unité de Renseignement et d'Action Électronique
## 1. Architecture Modulaire Intégrée
Le système n'est plus un simple noyau SIGINT, mais une plateforme de commandement centralisée reliant les modules suivants :
 * SOVEREIGN-CORE (src/core) : Le cerveau gérant le chiffrement AES-256 et le routage des données.
 * SIGINT (sigint/) : Module d'interception et de classification des menaces par IA.
 * BFT (bft/) : Suivi des forces alliées (Blue Force Tracking) pour éviter les tirs fratricides.
 * OFFENSIVE (Sovereign-Offensive/) : Arsenal de neutralisation cyber et d'opérations d'influence.
 * INFRA (infra/ & services/) : Monitoring de la santé du réseau Mesh et de l'infrastructure serveur.
## 2. Guide de Déploiement Rapide (Termux)
Pour lancer l'intégralité de l'arsenal, l'opérateur doit utiliser le script de déploiement automatisé :
cd ~/SOVEREIGN-CORE-PSC
./deploy.sh

Le script fusionne les sources, compile le Fat-JAR et active le serveur sur le port 7070.
## 3. Protocoles d'Accès Tactique
L'interface de commandement est accessible via tout navigateur sur le réseau sécurisé :
 * Tableau de Bord Global : http://localhost:7070/ (Redirection automatique vers l'interface tactique).
 * Statut des Menaces (API) : http://localhost:7070/api/status.
 * Interface SIGINT : Accès direct via /sigint/.
## 4. Sécurité et Résilience
 * SecurityVault : Chiffrement systématique des logs et des flux de données entrants.
 * Mesh Resilience : Le système peut fonctionner en mode dégradé sans internet, via le protocole Mesh de synchronisation.
 * Neutralisation : En cas de capture, l'arrêt du processus Java verrouille instantanément l'accès aux clés de déchiffrement.
 
> 5. SYSTÈME DE GESTION DE FICHIERS ET FLUX (WORKFLOW)
> Le SOVEREIGN CORE v2.4.0 centralise désormais l'intégralité du cycle de l'information :
>  * Ingestion (data/) : Stockage des signaux bruts et captures d'intelligence.
>  * Traitement (core/ & services/) : Algorithmes de décision et logique métier sécurisée.
>  * Action (Sovereign-Offensive/ & bft/) : Modules d'intervention cyber et de suivi des forces.
>  * Restitution (reports/) : Archivage des preuves et des synthèses tactiques.
>  * Maintenance (infra/) : Monitoring des ressources du terminal et du réseau Mesh.
> 
