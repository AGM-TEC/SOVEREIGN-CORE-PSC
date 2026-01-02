# 🛡️ MANUEL TACTIQUE SOVEREIGN CORE v4.0 (RÉVISION 2)
## SYSTÈME DE GUERRE ÉLECTRONIQUE ET SURVEILLANCE DISTRIBUÉE

### 1. PRÉSENTATION
La version 4.0 introduit la capacité **Hybrid-SDR** et le moteur **SignalStreamer**, permettant au commandement de visualiser instantanément le spectre électromagnétique via une interface HUD (Heads-Up Display).

### 2. NOUVELLES CAPACITÉS MATÉRIELLES & LOGICIELLES
* **Module Native-SDR (SovereignHardware)** : Accès direct aux drivers `librtlsdr`. Zéro latence pour l'interception de signaux critiques.
* **Module Network-Bridge (SdrBridge)** : Réception de flux radio via IP (WiFi/Mesh).
* **Moteur SignalStreamer (NOUVEAU)** : Diffusion en temps réel des données de signal vers le Dashboard via WebSocket sur la route `/api/signals/live`.

### 3. PROTOCOLE D'ENGAGEMENT (SITREP)
1.  **Démarrage** : Le système initialise le `SignalStreamer` au boot.
2.  **Liaison HUD** : Dès qu'un opérateur se connecte au Dashboard, le `SignalStreamer` établit un tunnel de données.
3.  **Analyse en Temps Réel** : Les pics de fréquence détectés sont projetés visuellement. Une signature identifiée comme ennemie déclenche une alerte visuelle immédiate.

### 4. VISUALISATION ET OFFENSIVE
* **HUD (Heads-Up Display)** : Affichage du spectre en "Cascade" (Waterfall) pour identifier les sauts de fréquence (Frequency Hopping) utilisés par les drones modernes.
* **Déploiement Offensif** : Depuis le HUD, l'opérateur peut sélectionner une fréquence suspecte et injecter un payload de brouillage via l'API `/api/offensive/deploy`.

### 5. SÉCURITÉ ET RÉSILIENCE
* **Encapsulation** : Les flux du SignalStreamer sont isolés et protégés par le `SecurityVault`.
* **Mode Autonome** : En cas de perte de connexion avec le HUD, le `Sovereign-Daemon` maintient l'interception en tâche de fond et enregistre les logs dans `/reports`.

---

## 🛡️ ADDENDUM v4.5 : CONTRÔLE TACTIQUE DES MODES
### 6. SÉLECTEUR DE POSTURE (CombatModeSelector)
L'opérateur dispose désormais d'un accès rapide aux postures de combat pour adapter l'empreinte électromagnétique du système à la situation sur le terrain.

#### 6.1 Définition des Postures
* **🟢 SILENT WATCH (Surveillance Passive)** : 
    * **Action** : Réception SIGINT seule.
    * **Usage** : Infiltration, repérage longue distance. 
    * **Avantage** : Indétectable par les systèmes de guerre électronique ennemis.
* **🟠 AUTO DEFENSE (Riposte Réactive)** : 
    * **Action** : Analyse IA active + Brouillage automatique.
    * **Usage** : Protection de convoi, détection de drones DJI/Autel.
    * **Avantage** : Temps de réaction réduit face aux menaces rapides.
* **🔴 ELECTRONIC ASSAULT (Interdiction de Zone)** : 
    * **Action** : Émission de bruit blanc haute puissance.
    * **Usage** : Rupture des communications ennemies, neutralisation de zone.
    * **Avantage** : Domination totale du spectre radio local.



### 7. INTERFACE DE COMMANDE HUD
Les boutons de contrôle situés sur le Dashboard (`/tactical/index.html`) envoient des directives prioritaires au **Sovereign-Daemon**. Chaque changement de mode est instantanément répercuté sur les modules SDR connectés.

> **⚠️ NOTE DE SÉCURITÉ** : Le passage en mode **ASSAULT** peut perturber les communications alliées si elles ne sont pas synchronisées via le module `MeshSyncEngine` avec saut de fréquence.
