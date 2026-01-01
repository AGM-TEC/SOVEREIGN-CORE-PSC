# 📘 Manuel Opérationnel – Combat-Ready System SIGINT


## Procédures Opérationnelles (SOP) – Mise à jour

### Mise en route
- Vérifier matériel : SDR (RTL-SDR, HackRF), antennes, batteries.
- Initialiser core/ et charger clés ZeroTrust.
- Vérifier disponibilité des bases de menaces dans data/ (fardc_threat_db.json, rdc_rebel_nets.json).
- Lancer l’interface opérateur (ui/StatusHUD.kt) pour affichage terrain.

### Capture et classification
- Activer module sigint/ pour interception radio.
- IA embarquée (services/SignalClassifier.kt via TensorFlow Lite) identifie modulation et type de signal (FM, AM, DMR, etc.).
- Détection anomalies via services/AnomalyDetector.kt.
- Géolocalisation via fusion_geo/ (TDOA/AoA).

### Transmission
- Priorité : réseau mesh sécurisé via infra/ et core/sync/WifiDirectAdapter.kt.
- Fallback : SMS chiffré ou HF analogique.
- Intégration automatique des ThreatMessage dans MeshSyncEngine.

### Sécurité
- Activer PanicHandler en cas de compromission.
- SensitiveStore.wipeAll() pour effacement complet ou Internal DoS.
- GeofenceManager : effacement automatique des clés hors zone autorisée.
- MissionLogger (core/audit/) : journal de mission enchaîné pour Evidence Mode.

### Audit et validation
- Vérification de l’intégrité des logs via MissionLogger.verifyIntegrity().
- Transmission des journaux chiffrés vers état-major.
- Tests de validation disponibles dans tests/ (panic_wipe_test.md, geofence validation).

### Interface opérateur
- Utiliser ui/StatusHUD.kt pour visualiser :
  - Statut du signal intercepté.
  - Alertes de zone (Geofence).
  - Logs critiques (Evidence Mode).
  - Messages Mesh reçus des camarades.

## Sécurité et Résilience – Mise à jour

### SensitiveStore
- Fonction : effacement complet des données sensibles.
- Si l’effacement échoue → déclenche Internal DoS (corruption massive + CPU Burn + extinction forcée).
- Valeur : garantit la non-récupération absolue des secrets militaires.

### PanicHandler
- Fonction : autodestruction immédiate en cas de compromission ou capture.
- Intégration : appelé par TacticalWipeManager.
- Valeur : neutralise l’appareil avant exploitation par l’ennemi.

### GeofenceManager
- Fonction : contrôle géographique via fichiers .poly.
- Si l’opérateur sort du périmètre autorisé (ex. Goma) → déclenche wipe automatique.
- Valeur : protection dynamique contre capture en zone rebelle.

### MissionLogger
- Fonction : journal de mission avec hachage enchaîné (Evidence Mode).
- Vérification : verifyIntegrity() détecte toute falsification.
- Valeur : preuve auditable et certifiable, utilisable en débriefing et tribunal militaire.

### ZeroTrustManager
- Fonction : chiffrement bout-en-bout des communications et des données.
- Valeur : aucune confiance implicite, chaque transaction est validée cryptographiquement.

### Cyber_resilience
- Fonction : redondance multi-canal (mesh, SMS, HF analogique).
- Autodiagnostic : surveillance continue de l’intégrité système.
- Valeur : maintien opérationnel même en conditions dégradées.

## Intelligence Artificielle embarquée – Mise à jour

### SignalClassifier (services/SignalClassifier.kt)
- Fonction : identifier automatiquement les modulations radio (FM, AM, DMR, etc.).
- Technologie : TensorFlow Lite, optimisé pour terminaux tactiques à ressources limitées.
- Usage : chaque détection est loggée via MissionLogger (Evidence Mode).
- Valeur : transforme les ondes brutes en informations exploitables pour l’opérateur.

### AnomalyDetector (services/AnomalyDetector.kt)
- Fonction : détecter des transmissions suspectes ou inconnues.
- Méthodes : Isolation Forest, autoencoder léger.
- Usage : alerte immédiate envoyée via MeshSyncEngine.
- Valeur : identification proactive des menaces non répertoriées dans data/fardc_threat_db.json.

### Flux opérationnel IA
1. Capture radio via sigint/
2. SignalClassifier → classification modulation
3. AnomalyDetector → détection anomalies
4. MissionLogger → enregistrement sécurisé (hachage enchaîné)
5. WifiDirectAdapter → transmission ThreatMessage aux unités proches
6. UI (StatusHUD.kt) → affichage en temps réel pour l’opérateur

### Valeur opérationnelle (FARDC)
- **Réactivité** : classification et détection en temps réel.
- **Automatisation** : réduit la charge cognitive des opérateurs.
- **Auditabilité** : chaque détection est enregistrée et vérifiable.
- **Interopérabilité** : résultats intégrés dans MeshSyncEngine pour diffusion immédiate.

## Formation et Certification – Mise à jour

### Documentation institutionnelle
- docs/ : contient les SOP (Standard Operating Procedures), manuels opérateurs, et guides de formation.
- specs/ : matrices de conformité, operational_spec, et documentation technique pour audit.
- SIGINT_System_Manual.md : manuel technique complet du système.
- manuel_op : manuel opérationnel en cours de rédaction (ce document).

### Manuel opérateur
- Procédures simplifiées pour usage terrain.
- Instructions pas-à-pas pour capture, classification, transmission et wipe.
- Interface UI (StatusHUD.kt) pour retour visuel immédiat.

### Compliance matrix
- Alignement avec normes OTAN et ITU.
- Vérification des exigences de sécurité, transmission et auditabilité.
- Documentation prête pour certification institutionnelle.

### Certification
- Audit interne via MissionLogger (Evidence Mode).
- Audit externe via specs/ et system_index.md.
- Transmission trilingue (FR/EN/ES) pour adoption continentale.

### Valeur opérationnelle (FARDC)
- Formation rapide des opérateurs grâce aux SOP et manuels simplifiés.
- Certification institutionnelle facilitée par documentation complète et auditable.
- Transmission et adoption au-delà du cadre national (continentalisation).

## SensitiveStore.kt – Internal DoS

### Objectif
Garantir la non-récupération absolue des données sensibles en cas de compromission.
Si l’effacement classique échoue (fichiers verrouillés, erreurs I/O), le système déclenche un Déni de Service Interne (Internal DoS).

### Fonctionnement
1. Effacement récursif :
   - Suppression des répertoires critiques :
     - data/signatures/
     - core/audit/logs/
     - data/reports/
     - data/keys/

2. Internal DoS :
   - Corruption massive : écriture en boucle de fichiers temporaires pour saturer le contrôleur de stockage.
   - CPU Burn : tous les cœurs sollicités à 100% → épuisement batterie et extinction forcée.
   - Notification critique : message d’alerte affiché avant extinction.
   - Exit Process : arrêt brutal du système après saturation.

### Intégration
- TacticalWipeManager appelle SensitiveStore.wipeAll().
- Si wipeAll() retourne false → Internal DoS est lancé automatiquement en arrière-plan.

### Valeur opérationnelle (FARDC)
- Garantie de non-récupération : même si l’ennemi bloque la suppression, les données deviennent irrécupérables.
- Réactivation du chiffrement matériel : extinction forcée → Full Disk Encryption reprend au redémarrage.
- Priorité maximale : les threads de sécurité passent avant toute tentative d’accès externe.
- Effet dissuasif : l’appareil devient une “brique” inutilisable, empêchant toute exploitation.

## GeofenceManager – Sécurité géographique

### Objectif
Empêcher l’utilisation du système SIGINT en dehors d’une zone de mission autorisée.
Si l’opérateur sort du périmètre défini, le TacticalWipeManager déclenche automatiquement l’effacement des données sensibles.

### Zone de mission : Goma (Nord-Kivu)
- Fichier actif : core/security/active_geofence.poly
- Coordonnées du polygone :
  - A : (-1.6393, 29.1947) – Entrée Nord de Goma (axe Rutshuru)
  - B : (-1.6385, 29.2554) – Frontière Rwanda (Grande Barrière)
  - C : (-1.6888, 29.2562) – Port de Goma
  - D : (-1.7082, 29.2155) – Péninsule du lac Kivu
  - E : (-1.6853, 29.1824) – Aéroport/Sake
  - Fermeture : retour au point A pour boucler la géométrie

### Procédures de test
Script Kotlin rapide :
kotlin
fun main() {
    GeofenceManager.loadGeofence("core/security/active_geofence.poly")

    // Test 1 : Centre de Goma → INSIDE
    val inGoma = GeofenceManager.isInAuthorizedZone(-1.6666, 29.2222)
    println("Position Goma Centre : ${if (inGoma) "AUTORISÉE" else "ALERTE WIPE"}")

    // Test 2 : Sake → OUTSIDE
    val inSake = GeofenceManager.isInAuthorizedZone(-1.6067, 29.0722)
    println("Position Sake : ${if (inSake) "AUTORISÉE" else "ALERTE WIPE"}")
}

## MissionLogger.kt – Evidence Mode

### Objectif
Garantir l’intégrité et la non-falsification des journaux de mission.
Chaque événement est enregistré avec un hachage enchaîné (chained hashing).
Toute modification ou suppression brise la chaîne et est immédiatement détectée.

### Fonctionnement
1. Hachage SHA-256 :
   - Chaque entrée contient : HASH_ACTUEL | LEVEL | EVENT | TIMESTAMP | HASH_PRECEDENT.
   - Le hash est recalculé à partir du contenu + hash précédent.

2. RecoverLastHash() :
   - Au redémarrage, le système retrouve le dernier hash valide pour continuer la chaîne.
   - En cas de corruption, la chaîne est marquée comme compromise.

3. verifyIntegrity() :
   - Vérifie l’intégrité complète du journal.
   - Retourne false si une falsification est détectée.

### Exemple d’utilisation
kotlin
MissionLogger.info("SIGINT capture: fréquence 29.222 MHz")
MissionLogger.critical("WIPE_TRIGGERED: sortie de zone Goma")
val integrityOk = MissionLogger.verifyIntegrity()
println("Intégrité du journal : ${if (integrityOk) "OK" else "COMPROMIS"}")

## WifiDirectAdapter.kt – Communication Mesh

### Objectif
Permettre aux unités SIGINT de communiquer directement entre elles, sans dépendre d’infrastructures civiles (antennes relais, routeurs).
Le Wi-Fi Direct (P2P) crée un réseau maillé autonome, idéal pour les opérations en forêt, montagne ou zones hostiles.

### Fonctionnement
1. Découverte des pairs :
   - manager.discoverPeers() recherche automatiquement les unités SIGINT à proximité.
   - Portée typique : ~200m en terrain dégagé.

2. Serveur d’écoute :
   - ServerSocket(8888) reçoit les messages entrants.
   - Les données reçues sont sérialisées (JSON/CBOR) en ThreatMessage.

3. Envoi de messages :
   - send(msg: ThreatMessage) diffuse les alertes et données aux pairs connectés.
   - Format binaire compact pour minimiser la bande passante.

4. Arrêt et gestion :
   - stop() interrompt la découverte des pairs.
   - receive(handler) permet de traiter les messages reçus et les intégrer dans l’UI ou la géolocalisation.

### Valeur opérationnelle (FARDC)
- Indépendance totale : pas besoin de routeur ni d’antenne → communication directe entre soldats.
- Discrétion : émission intermittente → réduit la probabilité de détection par l’ennemi.
- Vitesse : transfert rapide de fichiers lourds (extraits audio, spectrogrammes).
- Résilience : chaque appareil devient un nœud du mesh → pas de point unique de défaillance.

### Procédures associées
- Activation : lancer WifiDirectAdapter.start() au début de la mission.
- Transmission : utiliser send() pour partager menaces ou logs.
- Réception : configurer receive(handler) pour traiter les alertes en temps réel.
- Arrêt : exécuter stop() en fin de mission ou lors d’un wipe.

## SignalClassifier.kt – IA embarquée

### Objectif
Donner une intelligence embarquée au système : transformer les ondes radio interceptées en informations exploitables.
Le module SignalClassifier utilise TensorFlow Lite pour classifier automatiquement les modulations (FM, AM, DMR, etc.).

### Fonctionnement
1. Entrée : spectrogramme ou flux brut capturé par sigint/.
2. Traitement : modèle TensorFlow Lite optimisé pour terminaux tactiques.
3. Sortie : type de modulation identifié (FM, AM, DMR, etc.).
4. Intégration : chaque détection est loggée via MissionLogger (Evidence Mode).
5. Transmission : alertes envoyées automatiquement aux unités via WifiDirectAdapter.

### AnomalyDetector
- Fonction : détecter transmissions suspectes ou inconnues.
- Méthodes : Isolation Forest / autoencoder léger.
- Usage : alerte immédiate envoyée via MeshSyncEngine.
- Valeur : identification proactive des menaces non répertoriées dans data/fardc_threat_db.json.

### Flux opérationnel IA
1. Capture radio via sigint/
2. SignalClassifier → classification modulation
3. AnomalyDetector → détection anomalies
4. MissionLogger → enregistrement sécurisé (hachage enchaîné)
5. WifiDirectAdapter → transmission ThreatMessage aux unités proches
6. UI (StatusHUD.kt) → affichage en temps réel pour l’opérateur

### Valeur opérationnelle (FARDC)
- Réactivité : classification et détection en temps réel.
- Automatisation : réduit la charge cognitive des opérateurs.
- Auditabilité : chaque détection est enregistrée et vérifiable.
- Interopérabilité : résultats intégrés dans MeshSyncEngine pour diffusion immédiate.

## Modes opérationnels – SIGINT Combat-Ready

Ce chapitre regroupe tous les profils de mission disponibles dans le système SIGINT combat-ready.
Chaque mode est conçu pour répondre à un contexte opérationnel spécifique et active/désactive des modules précis.

### 🔒 Fallback Mode – Détail complet

#### Objectif
Assurer la transmission même en cas de perte totale de réseau civil ou militaire.
Ce mode garantit que les unités SIGINT peuvent continuer à échanger des informations critiques
même lorsque toutes les infrastructures classiques (antenne relais, routeur, Internet) sont indisponibles.

#### Modules actifs
- core/sync/MeshSyncEngine.kt  
  → moteur de communication maillée, basé sur Wi-Fi Direct, permettant la création d’un réseau P2P autonome.  
- services/transmission/FallbackTransmitter.kt  
  → module de transmission de secours, capable d’utiliser des canaux alternatifs (SMS chiffré, HF analogique, ou tout support disponible).

#### SOP associée
- docs/SOP/transmission_SOP.md  
  → décrit les procédures standard pour activer le mode fallback, tester la continuité de transmission,
  et valider la réception des ThreatMessage en conditions dégradées.

#### Valeur opérationnelle (FARDC)
- **Résilience totale** : communication maintenue même en cas de brouillage ou destruction des infrastructures.  
- **Continuité de mission** : aucune perte de données critiques, même en environnement hostile.  
- **Interopérabilité** : permet aux unités de fusionner leurs données sans dépendre d’un point central.  
- **Institutionnalisation** : SOP documentée et intégrée dans le manuel, prête pour adoption officielle.

### 🔋 Mode Low-Power – Détail complet

#### Objectif
Le Mode Low-Power est conçu pour prolonger l’autonomie des unités SIGINT lors de missions longues, en particulier dans des environnements où la recharge est impossible ou risquée.  
Il optimise la consommation énergétique tout en maintenant les fonctions essentielles, garantissant la continuité des opérations sur plusieurs jours.

#### Modules associés
- **core/power/LowPowerManager.kt** : module de gestion énergétique, réduit la fréquence CPU, désactive les capteurs non critiques et optimise les cycles de transmission.  
- **ui/tactical/NightVisionTheme.kt** : interface visuelle adaptée aux opérations nocturnes, faible luminosité pour limiter la consommation et préserver la discrétion.  

#### Procédures de fonctionnement
1. **Activation du mode basse consommation** : bascule automatique vers une configuration optimisée dès que l’autonomie descend sous un seuil critique.  
2. **Réduction des ressources** : désactivation des modules non essentiels (capteurs secondaires, logs détaillés).  
3. **Interface nocturne** : passage à un thème visuel discret, adapté à la vision nocturne et réduisant la consommation.  
4. **Surveillance continue** : suivi en temps réel de l’autonomie restante et ajustement dynamique des priorités.  

#### SOP associée
- **docs/SOP/power_SOP.md** : décrit les procédures pour activer le mode Low-Power, vérifier l’autonomie restante et ajuster les priorités de mission en fonction de l’énergie disponible.  

#### Valeur opérationnelle (FARDC)
- **Autonomie prolongée** : permet de maintenir les opérations sur plusieurs jours en terrain hostile.  
- **Discrétion visuelle** : interface adaptée à la vision nocturne, réduisant la signature lumineuse.  
- **Optimisation tactique** : désactive les modules non essentiels pour concentrer l’énergie sur la capture et la transmission critique.  
- **Survie opérationnelle** : garantit que même avec une batterie faible, les fonctions vitales (SIGINT, transmission) restent actives.  

#### Exemple de scénario
- **Situation** : une unité SIGINT est déployée en zone montagneuse sans possibilité de recharge pendant 72 heures.  
- **Action** : l’opérateur active le mode Low-Power, réduisant la consommation énergétique et basculant l’interface en thème nocturne.  
- **Résultat** : l’unité reste opérationnelle tout au long de la mission, assurant la capture et la transmission des signaux critiques malgré des ressources limitées.

### 🕶️ Mode Silent Ops – Détail complet

#### Objectif
Le Mode Silent Ops est conçu pour les opérations où la discrétion est vitale.  
Il minimise les traces numériques et visuelles afin de réduire la probabilité de détection par l’ennemi.  
Ce mode est utilisé lors des missions d’infiltration, de surveillance ou de collecte discrète de renseignements.

#### Modules associés
- **ui/tactical/LowLightRenderer.kt** : interface visuelle adaptée aux environnements nocturnes, faible luminosité pour réduire la signature visuelle.  
- **core/audit/MissionLogger.kt (journal minimal)** : enregistre uniquement les événements essentiels, limitant les traces exploitables tout en conservant une traçabilité minimale.  

#### Procédures de fonctionnement
1. **Activation de l’interface discrète** : bascule automatique vers un affichage à faible luminosité.  
2. **Journalisation réduite** : seuls les événements critiques sont enregistrés, sans détails superflus.  
3. **Minimisation des émissions** : limitation des transmissions numériques pour réduire la signature électronique.  

#### SOP associée
- **docs/SOP/silent_ops_SOP.md** : décrit les procédures pour activer le mode Silent Ops, ajuster la luminosité et vérifier la journalisation minimale.  

#### Valeur opérationnelle (FARDC)
- **Furtivité numérique** : réduit la quantité de données générées et stockées.  
- **Discrétion visuelle** : interface adaptée aux opérations nocturnes, minimisant la détection par observation directe.  
- **Sécurité opérationnelle** : journalisation minimale mais suffisante pour conserver une preuve en cas de débriefing.  
- **Adaptabilité tactique** : idéal pour missions d’infiltration ou observation prolongée sans révéler la présence SIGINT.  

#### Exemple de scénario
- **Situation** : une unité SIGINT doit observer discrètement une zone urbaine hostile.  
- **Action** : l’opérateur active le mode Silent Ops, réduisant la luminosité de l’interface et limitant les transmissions.  
- **Résultat** : la mission se déroule sans alerter l’ennemi, tout en conservant un journal minimal pour débriefing.

### 📑 Evidence Mode – Détail complet

#### Objectif
Collecter et tracer toutes les données critiques de mission avec un niveau de sécurité et de certification maximal.  
Ce mode est conçu pour les opérations où la preuve et l’auditabilité sont prioritaires : débriefing, certification institutionnelle, ou présentation devant état-major.

#### Modules actifs
- core/audit/MissionLogger.kt  
  → journal complet, chiffré et signé, basé sur hachage enchaîné (Evidence Mode).  
- data/reports/anomaly_report.md  
  → rapport automatique des anomalies détectées, intégré dans la documentation pour audit.

#### SOP associée
- docs/SOP/evidence_SOP.md  
  → décrit les procédures standard pour activer le mode Evidence, vérifier l’intégrité des journaux,
  et transmettre les rapports chiffrés à l’état-major ou aux instances de certification.

#### Valeur opérationnelle (FARDC)
- **Traçabilité totale** : chaque événement est enregistré, chiffré et signé.  
- **Auditabilité** : logs inviolables utilisables comme preuve devant tribunal militaire ou certification OTAN.  
- **Institutionnalisation** : documentation complète et prête pour adoption officielle.  
- **Débriefing renforcé** : permet d’analyser chaque étape de la mission avec preuves vérifiables.

### 🛰️ Mode Fusion & Géolocalisation – Détail complet

#### Objectif
Le Mode Fusion & Géolocalisation est conçu pour localiser avec précision un émetteur ennemi par triangulation et fusion des données SIGINT.  
Il combine les mesures de plusieurs unités pour obtenir une position exacte, permettant une action rapide et coordonnée.  
Ce mode est essentiel pour les opérations de neutralisation, de surveillance ciblée et de sécurisation des zones sensibles.

#### Modules associés
- **comint/geo/TDOA_Engine.kt** : moteur de calcul basé sur le Time Difference of Arrival (TDOA), permettant la triangulation des signaux interceptés.  
- **fusion_geo/** : module de fusion géospatiale, combine les données de plusieurs capteurs et unités pour améliorer la précision.  

#### Procédures de fonctionnement
1. **Capture des signaux** : chaque unité SIGINT enregistre les signaux ennemis avec horodatage précis.  
2. **Calcul TDOA** : le moteur détermine la différence de temps d’arrivée entre les signaux captés par différentes unités.  
3. **Fusion des données** : les résultats sont combinés via le module fusion_geo pour obtenir une localisation consolidée.  
4. **Validation cartographique** : comparaison avec les cartes offline pour confirmer la position et réduire les marges d’erreur.  

#### SOP associée
- **docs/SOP/fusion_geo_SOP.md** : décrit les procédures pour activer le mode Fusion & Géolocalisation, synchroniser les unités et valider la triangulation.  

#### Valeur opérationnelle (FARDC)
- **Précision tactique** : localisation exacte des émetteurs ennemis, même en environnement complexe.  
- **Coordination multi-unités** : fusion des données de plusieurs opérateurs pour renforcer la fiabilité.  
- **Support direct aux opérations** : fournit des coordonnées exploitables pour neutralisation ou interception.  
- **Institutionnalisation** : SOP documentée, intégrée dans le manuel, prête pour adoption officielle.  

#### Exemple de scénario
- **Situation** : plusieurs unités SIGINT interceptent un signal suspect dans une zone frontalière.  
- **Action** : le TDOA Engine calcule les différences de temps d’arrivée, fusion_geo combine les résultats.  
- **Résultat** : la position exacte de l’émetteur ennemi est déterminée et transmise à l’état-major pour action immédiate.

### 🤖 Mode Anomaly Detection (IA Locale) – Détail complet

#### Objectif
Le Mode Anomaly Detection est conçu pour identifier automatiquement les comportements radio suspects ou non répertoriés dans les bases de menaces connues.  
Il permet une détection proactive des transmissions anormales, réduisant le temps de réaction des unités SIGINT et renforçant la sécurité opérationnelle.  
Ce mode est essentiel pour anticiper les menaces émergentes et compléter les capacités de classification standard.

#### Modules associés
- **services/dsp/ai_inference/AnomalyDetector.kt** : moteur d’inférence IA embarqué, basé sur des algorithmes légers (Isolation Forest, autoencoder).  
- **data/signatures/anomalies.json** : base de signatures enrichie en continu par les retours terrain et les détections IA.  
- **MissionLogger.kt (Evidence Mode)** : journalise chaque anomalie détectée pour garantir traçabilité et certification.  

#### Procédures de fonctionnement
1. **Analyse en temps réel** : l’IA embarquée surveille les spectres radio et compare les signaux aux signatures connues.  
2. **Détection d’anomalies** : identification des transmissions suspectes ou non répertoriées.  
3. **Alerte immédiate** : notification transmise aux unités voisines via MeshSyncEngine.  
4. **Journalisation** : enregistrement des anomalies dans MissionLogger pour audit et débriefing.  

#### SOP associée
- **docs/SOP/anomaly_SOP.md** : décrit les procédures pour activer le mode Anomaly Detection, valider les alertes générées par l’IA et transmettre les rapports aux unités voisines.  

#### Valeur opérationnelle (FARDC)
- **Détection proactive** : identification des menaces non répertoriées dans les bases classiques.  
- **Réactivité accrue** : alerte immédiate transmise aux unités proches pour action rapide.  
- **Auditabilité** : chaque anomalie détectée est enregistrée et certifiée.  
- **Institutionnalisation** : SOP documentée, intégrée dans le manuel, prête pour adoption officielle.  

#### Exemple de scénario
- **Situation** : une unité SIGINT intercepte un signal inhabituel qui ne correspond à aucune modulation connue.  
- **Action** : l’AnomalyDetector analyse le spectre et identifie un comportement suspect avec un score de confiance élevé.  
- **Résultat** : une alerte est immédiatement transmise aux unités voisines via MeshSyncEngine, et MissionLogger enregistre l’événement pour débriefing et certification.

## 13. Modes de combat opérationnel – Implémentation

Le système SIGINT combat-ready repose sur plusieurs modes opérationnels, chacun conçu pour répondre à un contexte tactique précis.  
Ces modes constituent la doctrine d’emploi du système et garantissent flexibilité, sécurité et efficacité sur le terrain.

---

### ### 🔒 Mode Panic Wipe – Détail complet

#### Objectif
Le Mode Panic Wipe est conçu pour protéger immédiatement toutes les données sensibles du système SIGINT en cas de compromission.  
Il s’active automatiquement lorsque l’unité sort de la zone géographique autorisée, lorsqu’une clé de détresse est saisie par l’opérateur, ou lorsqu’un sabotage matériel est détecté.  
Son rôle est de garantir qu’aucune information stratégique ne puisse tomber entre les mains adverses.

#### Modules associés
- **TacticalWipeManager.kt** : cœur du mécanisme d’effacement, orchestre la suppression des données et des clés.  
- **GeofenceManager.kt** : vérifie la position de l’unité par rapport au périmètre défini dans `active_geofence.poly`.  
- **KeyVault** : gère et détruit les clés cryptographiques (master.key, session.key).  
- **SensitiveStore** : efface les répertoires critiques (signatures radio, rapports, journaux).  
- **MissionLogger.kt** : journalise chaque déclenchement et résultat du wipe en mode Evidence.

#### Procédures de déclenchement
1. **Sortie de zone (Geofence)** : si l’opérateur franchit le périmètre défini, le wipe est déclenché.  
2. **Clé de détresse** : saisie manuelle par l’opérateur en cas de capture imminente.  
3. **Tamper matériel** : détection d’une tentative de sabotage ou d’ouverture non autorisée du dispositif.  

#### SOP associée
- **docs/SOP/panicwipeSOP.md** : décrit les étapes d’activation, les tests de déclenchement et les vérifications post-effacement.  
- Inclut les scénarios de simulation pour valider la robustesse du mécanisme.

#### Valeur opérationnelle (FARDC)
- **Neutralisation immédiate** : aucune donnée exploitable ne subsiste après déclenchement.  
- **Sécurité stratégique** : protège les bases de signatures, clés et rapports sensibles.  
- **Traçabilité** : chaque effacement est enregistré dans MissionLogger pour audit et certification.  
- **Institutionnalisation** : SOP documentée, intégrée dans le manuel, prête pour adoption officielle.  

#### Exemple de scénario
- **Situation** : une unité SIGINT est encerclée et risque d’être capturée.  
- **Action** : l’opérateur saisit la clé de détresse.  
- **Résultat** : TacticalWipeManager efface immédiatement les clés, les signatures et les rapports, journalise l’événement, puis neutralise le système.

---

### 📑 Mode Evidence – Détail complet

#### Objectif
Le Mode Evidence est conçu pour assurer une traçabilité inviolable et certifiable de toutes les opérations critiques.  
Il garantit que chaque événement, chaque effacement et chaque détection est enregistré de manière sécurisée, chiffrée et signée.  
Ce mode est essentiel pour les missions où la preuve et l’auditabilité doivent être garanties, que ce soit pour un débriefing militaire, une certification institutionnelle ou une présentation devant un tribunal.

#### Modules associés
- **MissionLogger.kt** : journal complet basé sur hachage enchaîné et signature cryptographique.  
- **data/reports/anomaly_report.md** : rapport automatique des anomalies détectées, intégré dans la documentation pour audit.  
- **SensitiveStore & KeyVault (hooks)** : assurent que les effacements sont également tracés et certifiés.  

#### Procédures de fonctionnement
1. **Journalisation complète** : chaque événement est enregistré avec un horodatage et un hash lié au précédent.  
2. **Signature cryptographique** : chaque entrée est signée pour empêcher toute falsification.  
3. **Rotation des logs** : gestion automatique des fichiers pour éviter la surcharge et garantir la continuité.  
4. **Rapports d’anomalies** : intégration automatique des détections IA et des événements critiques dans les rapports.  

#### SOP associée
- **docs/SOP/evidence_SOP.md** : décrit les procédures pour activer le mode Evidence, vérifier l’intégrité des journaux et transmettre les rapports chiffrés à l’état-major ou aux instances de certification.  

#### Valeur opérationnelle (FARDC)
- **Traçabilité totale** : chaque action est enregistrée et vérifiable.  
- **Auditabilité inviolable** : logs utilisables comme preuve devant tribunal militaire ou certification OTAN.  
- **Débriefing renforcé** : permet d’analyser chaque étape de la mission avec preuves vérifiables.  
- **Institutionnalisation** : documentation complète et prête pour adoption officielle.  

#### Exemple de scénario
- **Situation** : une unité SIGINT intercepte une transmission suspecte.  
- **Action** : le SignalClassifier identifie la modulation et l’AnomalyDetector signale une anomalie.  
- **Résultat** : MissionLogger enregistre l’événement avec hash et signature, anomaly_report.md est généré, et l’état-major reçoit une preuve inviolable de la détection.

---

### 🌐 Mode MeshSync – Détail complet

#### Objectif
Le Mode MeshSync est conçu pour assurer la continuité des communications entre unités SIGINT, même en l’absence d’infrastructure civile ou militaire.  
Il repose sur une architecture maillée (mesh network) permettant le partage immédiat des données de menace (ThreatMessage) et la synchronisation des informations critiques.  
Ce mode est vital pour garantir la résilience des opérations en terrain hostile ou isolé.

#### Modules associés
- **MeshSyncEngine.kt** : moteur central de communication maillée, abstrait les différents transports.  
- **MessageEnvelope.kt (CBOR)** : format compact et standardisé pour encapsuler les ThreatMessage.  
- **TransportAdapter** : interface de transport adaptable (Wi‑Fi Direct, LoRa, ou autres).  
- **CRDT légère** : mécanisme de fusion des données pour éviter les conflits et assurer la cohérence entre unités.  

#### Procédures de fonctionnement
1. **Initialisation du réseau maillé** : chaque unité démarre son transport (Wi‑Fi Direct ou LoRa).  
2. **Diffusion des ThreatMessage** : les données interceptées sont encapsulées et partagées automatiquement.  
3. **Store‑and‑forward** : les messages sont stockés localement et retransmis dès qu’une connexion est disponible.  
4. **Fusion des données (CRDT)** : les informations reçues sont intégrées sans perte ni duplication.  

#### SOP associée
- **docs/SOP/transmission_SOP.md** : décrit les procédures pour activer MeshSync, tester la diffusion locale et valider la cohérence des données partagées.  

#### Valeur opérationnelle (FARDC)
- **Résilience des communications** : garantit le partage d’informations même en cas de brouillage ou destruction des infrastructures.  
- **Interopérabilité** : permet aux unités de communiquer sans dépendre d’un point central.  
- **Réactivité tactique** : diffusion immédiate des menaces détectées à toutes les unités connectées.  
- **Institutionnalisation** : SOP documentée, intégrée dans le manuel, prête pour adoption officielle.  

#### Exemple de scénario
- **Situation** : une unité SIGINT détecte une transmission suspecte en zone isolée.  
- **Action** : MeshSync encapsule la menace dans un MessageEnvelope et la diffuse via Wi‑Fi Direct.  
- **Résultat** : les unités voisines reçoivent l’alerte en temps réel, même sans réseau civil, et peuvent coordonner une réponse immédiate.

---

### 🤖 Mode IA – SignalClassifier – Détail complet

#### Objectif
Le Mode IA – SignalClassifier est conçu pour classifier automatiquement les signaux interceptés grâce à un modèle d’intelligence artificielle embarqué.  
Il permet d’identifier rapidement la modulation et la nature des transmissions radio, offrant un avantage tactique décisif en réduisant le temps nécessaire à l’analyse humaine.  
Ce mode constitue la première étape vers une détection proactive des anomalies et menaces radio.

#### Modules associés
- **SignalClassifier.kt** : charge et exécute un modèle TensorFlow Lite pour classification des spectres.  
- **Modèle IA (TFLite)** : fichier de modèle pré-entraîné, optimisé pour terminaux tactiques.  
- **Integration avec AnomalyDetector** : prépare les résultats pour être exploités par le module de détection d’anomalies.  

#### Procédures de fonctionnement
1. **Chargement du modèle IA** : vérification de la présence et de l’intégrité du fichier TFLite.  
2. **Classification des spectres** : analyse des données radio interceptées et attribution d’une modulation (ex. VHF_FM, AM, PSK).  
3. **Retour de confiance** : chaque classification est accompagnée d’un score de confiance.  
4. **Transmission des résultats** : les données classifiées sont envoyées vers MissionLogger et MeshSyncEngine pour diffusion.  

#### SOP associée
- **docs/SOP/anomaly_SOP.md** : décrit les procédures pour activer le SignalClassifier, valider les résultats et transmettre les classifications aux unités voisines.  

#### Valeur opérationnelle (FARDC)
- **Gain de temps** : classification immédiate des signaux sans intervention humaine.  
- **Préparation à la détection proactive** : résultats exploitables directement par l’AnomalyDetector.  
- **Interopérabilité** : classifications partagées via MeshSyncEngine pour coordination multi-unités.  
- **Institutionnalisation** : SOP documentée, intégrée dans le manuel, prête pour adoption officielle.  

#### Exemple de scénario
- **Situation** : une unité SIGINT intercepte un signal inconnu sur une fréquence VHF.  
- **Action** : le SignalClassifier analyse le spectre et identifie la modulation comme VHF_FM avec une confiance de 72 %.  
- **Résultat** : MissionLogger enregistre la classification, MeshSyncEngine diffuse l’information aux unités voisines, et l’AnomalyDetector est prêt à vérifier si le comportement est suspect.

---

### 🧪 Tests d’intégration
- **Scénarios validés** :  
  - Géofence hors zone → déclenchement du wipe.  
  - Clé de détresse → déclenchement du wipe.  
  - Tamper matériel → déclenchement du wipe.  
- **Résultats attendus** :  
  - Suppression des clés et signatures.  
  - Vidage des données sensibles.  
  - Journalisation CRITICAL dans MissionLogger.  

---

### Valeur stratégique globale
Ces modes de combat opérationnel assurent :  
- **Résilience** : continuité des missions même en conditions dégradées.  
- **Sécurité** : effacement automatique et traçabilité inviolable.  
- **Interopérabilité** : communication maillée entre unités.  
- **Innovation** : intégration de l’IA pour classification et détection proactive.

## 🌐 Mode MeshSync – Protocole Threat Exchange

### Objectif
Le Mode MeshSync assure la continuité des communications entre unités SIGINT en l’absence d’infrastructure civile ou militaire.  
Il repose sur un protocole d’échange opportuniste et compact, permettant aux patrouilles de partager automatiquement leurs découvertes (fréquences suspectes, positions ennemies, anomalies détectées) dès qu’elles se croisent sur le terrain.

### Modules associés
- **MeshSyncEngine.kt** : moteur central de synchronisation store‑and‑forward.  
- **MessageEnvelope (CBOR)** : format binaire compact pour encapsuler les ThreatMessage.  
- **TransportAdapter** : interface adaptable (Wi‑Fi Direct, LoRa, Bluetooth).  
- **Cache local & déduplication** : stockage temporaire des menaces et suppression des doublons pour éviter la saturation.  

### Procédures de fonctionnement
1. **Détection de pair** : dès qu’un terminal voisin est détecté via Wi‑Fi Direct ou LoRa, la synchronisation démarre automatiquement.  
2. **Diffusion priorisée** : les menaces CRITICAL sont transmises en premier, suivies des logs INFO.  
3. **Store‑and‑forward** : les menaces détectées sont stockées localement et retransmises dès qu’une connexion est disponible.  
4. **Déduplication** : chaque message est identifié par un UUID unique, évitant les boucles et doublons dans le réseau maillé.  

### SOP associée
- **docs/SOP/transmission_SOP.md** : décrit les procédures pour activer MeshSync, tester la diffusion opportuniste et valider la cohérence des données échangées.  

### Valeur opérationnelle (FARDC)
- **Résilience des communications** : continuité d’échange même en forêt ou en zone isolée.  
- **Interopérabilité** : coordination multi‑unités sans dépendre d’un point central.  
- **Réactivité tactique** : diffusion immédiate des menaces critiques à toutes les unités connectées.  
- **Optimisation énergétique** : format binaire compact pour économiser batterie et temps de transmission.  
- **Institutionnalisation** : protocole standardisé, intégré dans le manuel, prêt pour adoption officielle.  

### Exemple de scénario
- **Situation** : deux patrouilles SIGINT se croisent en forêt sans réseau civil.  
- **Action** : MeshSync détecte automatiquement la présence d’un pair et échange les menaces stockées (fréquences suspectes, positions rebelles).  
- **Résultat** : en quelques secondes, les deux unités disposent d’une base commune de menaces actualisée, renforçant leur coordination et leur efficacité.

## 🤖 Mode IA – SignalClassifier – Détail complet

#### Objectif
Le Mode IA – SignalClassifier est conçu pour agir comme un filtre automatique, permettant à l’opérateur de se concentrer sur la mission plutôt que sur l’analyse manuelle des spectrogrammes.  
Il analyse les données IQ issues de l’antenne SDR, identifie la modulation, et lorsqu’elle correspond à une menace connue, génère automatiquement un **ThreatMessage** pour le MeshSyncEngine.  
Ce mode boucle le cycle OODA (Observer – Orienter – Décider – Agir) de manière logicielle, garantissant une supériorité décisionnelle sur le terrain.

#### Modules associés
- **SignalClassifier.kt** : moteur IA embarqué basé sur TensorFlow Lite, chargé de la classification des signaux.  
- **SDR Capture Module** : collecte les données IQ brutes du spectre radio.  
- **MissionLogger.kt** : journalise et signe chaque détection pour assurer traçabilité et certification.  
- **MeshSyncEngine.kt** : met en file d’attente et diffuse les ThreatMessage aux unités voisines.  

#### Procédures de fonctionnement
1. **Observer** : le module SDR capture le signal brut.  
2. **Orienter** : le SignalClassifier identifie la modulation (ex. DMR, AM, PSK).  
3. **Décider** : le système calcule un score de confiance (ex. 92 %) et juge si l’alerte doit être déclenchée.  
4. **Agir** : un ThreatMessage est généré, signé par MissionLogger, et transmis via MeshSyncEngine.  

#### SOP associée
- **docs/SOP/anomaly_SOP.md** : décrit les procédures pour activer le SignalClassifier, valider les résultats IA et transmettre les alertes aux unités voisines.  

#### Valeur opérationnelle (FARDC)
- **Gain de temps** : classification et alerte instantanées, là où un opérateur humain mettrait plusieurs minutes.  
- **Précision renforcée** : couplage des détections IA avec les coordonnées GPS pour créer une carte de chaleur des menaces en temps réel.  
- **Supériorité décisionnelle** : passage direct de l’onde physique (spectre) à l’objet numérique (ThreatMessage), sans intervention humaine.  
- **Interopérabilité** : intégration transparente avec MissionLogger et MeshSyncEngine pour diffusion immédiate.  
- **Institutionnalisation** : SOP documentée, intégrée dans le manuel, prête pour adoption officielle.  

#### Exemple de scénario
- **Situation** : une unité SIGINT intercepte un signal inconnu sur une fréquence VHF.  
- **Action** : le SignalClassifier identifie automatiquement une modulation DMR avec une confiance de 92 %.  
- **Résultat** : un ThreatMessage est généré, signé et mis en file d’attente dans MeshSyncEngine. Les unités voisines reçoivent l’alerte en quelques secondes, renforçant la coordination tactique.

## 🔐 Chaîne de Confiance – Scripts & Makefile

### Objectif
Le dossier `scripts/` et le Makefile consolidé constituent la touche finale de l’architecture SIGINT Combat‑Ready.  
Ils assurent le durcissement des binaires, la rotation des clés, la vérification d’intégrité et le nettoyage forensic, garantissant que le système reste inviolable même en cas de capture ou de tentative de sabotage.

---

### Scripts de sécurité

#### harden_binary.sh
- **Rôle** : transforme le code lisible en un bloc obscurci et protégé.  
- **Effets** :
  - **Déni d’analyse** : empêche l’ingénierie inverse des fréquences surveillées et des algorithmes.  
  - **Protection contre le tampering** : injecte une signature d’intégrité pour bloquer toute modification malveillante.  
  - **Nettoyage forensic** : supprime les symboles de debug et les métadonnées sensibles.  

#### rotate_keys.sh
- **Rôle** : régénère les clés cryptographiques avant chaque mission.  
- **Valeur opérationnelle** : empêche l’utilisation prolongée d’une même clé, réduisant le risque de compromission.  
- **Best practice militaire** : rotation régulière des clés via `openssl`.  

#### integrity_check.sh
- **Rôle** : vérifie l’intégrité du binaire à chaque démarrage.  
- **Valeur opérationnelle** : détecte toute tentative d’injection de backdoor ou de sabotage.  
- **Usage terrain** : l’opérateur peut lancer `make check-integrity` avant patrouille pour validation rapide.  

#### clean_logs.sh
- **Rôle** : efface les journaux sensibles après mission ou avant transfert d’équipement.  
- **Valeur opérationnelle** : garantit la sanitisation et empêche la fuite d’informations stratégiques.  

---

### 🚀 Makefile Final – Tableau de Bord du Système

Le Makefile consolidé devient le **point d’entrée unique** pour piloter l’ensemble de l’architecture.  
Il est conçu pour être scannable, modulaire et audit‑ready, réduisant les erreurs humaines sous stress.

#### Caractéristiques
- **Automatisation de la signature** : chaque compilation génère automatiquement `specs/integrity_signature.txt`, assurant la traçabilité et la référence pour les futurs tests d’intégrité.  
- **Modularité** : l’opérateur peut exécuter des commandes ciblées (`make check-integrity`, `make clean-logs`) sans recompiler tout le système.  
- **Auditabilité** : chaque commande renvoie un statut clair ([OK], [ALERTE]), facilitant la prise de décision rapide en opération.  

---

### Valeur opérationnelle (FARDC)
- **Chaîne de confiance complète** : du code source au binaire durci, chaque étape est sécurisée et vérifiable.  
- **Résilience contre l’adversaire** : obscurcissement, signatures et contrôles empêchent l’analyse et le sabotage.  
- **Autonomie de l’opérateur** : procédures simplifiées, utilisables sans expertise cryptographique.  
- **Institutionnalisation** : SOP documentées et intégrées, prêtes pour adoption officielle.  

---

### Exemple de scénario
- **Situation** : une unité SIGINT revient de mission et doit transférer son matériel à une autre patrouille.  
- **Action** : l’opérateur exécute `make clean-logs` pour effacer les journaux, puis `make rotate-keys` pour générer de nouvelles clés.  
- **Résultat** : le terminal est remis en état sécurisé, prêt pour une nouvelle mission, avec une intégrité vérifiée et une traçabilité garantie.

## 🔗 Fusion BFT + SIGINT – Architecture intégrée

### Objectif
La fusion du système **BFT (Blue Force Tracking)** et du système **SIGINT combat‑ready** constitue l’ossature complète d’une unité mobile de guerre électronique.  
Elle combine la conscience situationnelle interne (forces amies) et externe (spectre ennemi), permettant une coordination optimale et une supériorité décisionnelle.

---

### Différences fondamentales

| Aspect              | BFT (Blue Force Tracking)                         | SIGINT combat‑ready system                          |
|---------------------|---------------------------------------------------|----------------------------------------------------|
| Mission principale  | Localiser et coordonner les forces amies.         | Intercepter, analyser et exploiter les signaux adverses. |
| Nature des données  | Positions GPS, identifiants d’unités, messages.   | Flux radio, spectre électromagnétique, métadonnées. |
| Finalité            | Conscience situationnelle interne.                | Renseignement et guerre électronique.              |
| Approche technique  | Cartographie, transport résilient, chiffrement.   | Capture RF, analyse spectrale, brouillage.         |
| Effet opérationnel  | Coordination et sécurité des troupes.             | Neutralisation des communications ennemies.        |
| Compatibilité       | Radios civiles/militaires.                        | SDR, antennes, modules de brouillage.              |

---

### Articulation des deux systèmes
- **BFT →** “Où sont nos forces ?”  
- **SIGINT →** “Que fait l’ennemi dans le spectre ?”  
- **Fusion →** Carte tactique enrichie :  
  - Forces amies (BFT).  
  - Menaces spectrales (SIGINT).  
  - Zones brouillées et sources hostiles.  

---

### Architecture fusionnée

#### 1. Niveau Terrain (Unités & Capteurs)
- GPS & terminaux BFT → localisation des forces amies.  
- Radios (LoRa, VHF/UHF, SATCOM) → transmission des données BFT.  
- Antennes SDR → capture du spectre électromagnétique.  
- Capteurs brouillage/détection → identification des menaces électroniques.  

#### 2. Niveau Logiciel (Modules principaux)
- **BFT Core** : positions, messages, cartographie hors‑ligne, chiffrement.  
- **SIGINT Core** : capture RF, analyse spectrale, cartographie des menaces, brouillage défensif/offensif.  
- **TransportAdapter** : abstraction des radios, bascule automatique, store‑and‑forward.  
- **Security & Audit** : chiffrement bout‑en‑bout, rotation des clés, logs append‑only avec hash‑chain.  

#### 3. Niveau Serveur (Commandement)
- Agrégation des données BFT et SIGINT.  
- Carte enrichie : forces amies + menaces spectrales.  
- Redistribution en temps réel aux unités.  
- Documentation certifiable pour audit institutionnel.  

#### 4. Niveau Commandement (Décision)
- Interface unique : carte tactique enrichie.  
- Double conscience situationnelle :  
  - Forces amies (BFT).  
  - Menaces électroniques (SIGINT).  
- Actions possibles : repositionnement, brouillage ciblé, contre‑mesures.  

---

### Valeur opérationnelle (FARDC)
- **Coordination interne** : réduction du chaos, meilleure sécurité des troupes.  
- **Supériorité externe** : interception et neutralisation des communications ennemies.  
- **Fusion stratégique** : décisions basées sur une double conscience situationnelle.  
- **Institutionnalisation** : architecture documentée et prête pour adoption officielle.  

---

### Vision finale
Une unité mobile de guerre électronique équipée de ce système :  
- Coordonne ses forces via BFT modulaire.  
- Surveille et neutralise l’ennemi via SIGINT combat‑ready.  
- Documente chaque action pour audit et certification.  
- Fonctionne en tout terrain, avec radios civiles ou militaires.

## 🖇️ Common Operating Picture (COP) – UnifiedMessage

### Objectif
Le format **UnifiedMessage** est la pierre angulaire du Common Operating Picture (COP).  
Il permet de synchroniser dans un flux unique les données issues du BFT (forces amies) et du SIGINT (menaces ennemies), garantissant une conscience situationnelle complète et partagée entre toutes les unités.

---

### Principes clés

#### Indifférence au transport
- Le **MeshSyncEngine** reçoit un UnifiedMessage sans distinction de type (position GPS ou fréquence radio).  
- Il vérifie uniquement la **signature cryptographique**.  
- Si le message est valide, il est relayé aux autres unités, indépendamment du canal utilisé (Wi‑Fi Direct, LoRa, SATCOM).

#### Sécurité par compartimentage
- Le **payload** est encapsulé sous forme de **ByteArray**.  
- Chaque payload peut être chiffré indépendamment avec une clé de session.  
- Même si une partie du système est compromise, les données brutes restent illisibles sans la clé appropriée.

#### Auditabilité totale
- Chaque message porte la **signature de son émetteur**.  
- Le **MissionLogger** enregistre une trace certifiable et inviolable, par exemple :  
  - *“L’unité ALPHA‑01 a signalé une menace DMR à 14h05”*.  
- Cette traçabilité garantit la valeur probante des données pour débriefing et certification.

---

### Exemple d’utilisation – Fusion BFT + SIGINT

1. **BFT Core** génère un UnifiedMessage contenant la position GPS d’une unité amie.  
2. **SIGINT Core** génère un UnifiedMessage contenant une menace radio détectée (ex. DMR).  
3. Les deux messages sont encapsulés, signés et transmis via MeshSyncEngine.  
4. Les unités voisines reçoivent un flux unique où :  
   - Les positions amies (BFT) et  
   - Les menaces ennemies (SIGINT)  
   sont synchronisées dans le même paquet.

---

### Résultat de la fusion
- L’opérateur dispose désormais d’un **flux de données unifié**.  
- La carte tactique affiche simultanément :  
  - Les forces amies (BFT).  
  - Les menaces spectrales (SIGINT).  
- Le COP est ainsi concrétisé : une **vision commune**, partagée et certifiée, qui aligne toutes les unités sur la même réalité opérationnelle.

---

### Valeur opérationnelle (FARDC)
- **Conscience situationnelle totale** : forces amies + menaces ennemies dans un flux unique.  
- **Interopérabilité** : format standardisé, indépendant du transport.  
- **Sécurité renforcée** : compartimentage et chiffrement des payloads.  
- **Auditabilité** : chaque message signé et journalisé pour certification.  
- **Institutionnalisation** : adoption du COP comme doctrine d’emploi officielle.

## 🛰️ Flux de Données – De l’Onde au Mesh (Chain of Intelligence)

### Objectif
Ce flux illustre comment une onde captée par une antenne SDR en terrain hostile (ex. jungle du Kivu) devient un paquet binaire optimisé circulant dans le réseau Mesh.  
Il concrétise la **supériorité décisionnelle** en transformant un signal brut en renseignement exploitable, certifié et diffusé en temps réel.

---

### Agilité spectrale – CBOR & Protobuf
- **Protobuf** : utilisé pour les échanges rapides via Wi‑Fi Direct, lorsque les unités sont proches et que le débit est élevé.  
- **CBOR** : utilisé pour les messages critiques envoyés via LoRa, lorsque la portée et la discrétion sont prioritaires.  
- **Résultat** : un système agile, capable d’adapter son codec en fonction du contexte opérationnel.

---

### Caractéristiques du flux

#### Découplage total
- **SignalClassifier** : analyse et génère un ThreatMessage sans se soucier du transport.  
- **TransportAdapter** : relaie le message sans connaître son contenu.  
- **Modularité** : permet de changer de radio ou d’IA sans modifier l’architecture globale.

#### Furtivité électromagnétique
- **PacketCodec** : réduit la durée d’émission (airtime) au minimum.  
- **Effet tactique** : moins de temps d’émission = moins de chances d’être repéré par la goniométrie ennemie.  

#### Légalité et preuve
- **MissionLogger** : enregistre chaque classification dès l’origine.  
- **Auditabilité** : même si un message est perdu dans le Mesh, une trace immuable existe sur le terminal source.  
- **Valeur institutionnelle** : preuve certifiable pour débriefing et audit post‑mission.  

---

### Chaîne de l’intelligence – Étapes

1. **Capture** : l’antenne SDR saisit l’onde brute.  
2. **Classification (SignalClassifier)** : l’IA identifie modulation et menace.  
3. **Journalisation (MissionLogger)** : l’événement est signé et horodaté.  
4. **Encapsulation (UnifiedMessage)** : le message est transformé en paquet binaire (CBOR ou Protobuf).  
5. **Transmission (MeshSyncEngine)** : diffusion opportuniste via Wi‑Fi Direct ou LoRa.  
6. **Fusion COP** : intégration simultanée des données BFT (forces amies) et SIGINT (menaces ennemies) dans un flux unique.  

---

### 🏁 Bilan – Plateforme intégrée
Le système combat‑ready SIGINT + BFT constitue une **plateforme intégrée** :  
- **Capteurs** : SDR (SIGINT) & GPS (BFT).  
- **Intelligence** : classification IA & fusion COP.  
- **Transport** : MeshSyncEngine, codecs CBOR/Protobuf, adapters multi‑radios.  
- **Sécurité** : Panic Wipe, HMAC, chiffrement, rotation des clés.  

---

### Valeur opérationnelle (FARDC)
- **Agilité spectrale** : adaptation automatique du codec selon contexte (rapidité vs portée).  
- **Supériorité décisionnelle** : transformation instantanée du spectre en renseignement exploitable.  
- **Furtivité** : réduction de l’airtime, minimisation du risque de détection.  
- **Auditabilité totale** : chaque message est signé et journalisé, garantissant une traçabilité inviolable.  
- **Institutionnalisation** : architecture documentée et prête pour adoption officielle.

## 🎯 FusionOverlay.kt – Interface tactique décisionnelle

### Objectif
Le module **FusionOverlay.kt** est la dernière étape de la plateforme SIGINT + BFT.  
Il transforme les paquets binaires (CBOR/Protobuf) en objets graphiques standardisés sur la carte tactique, permettant à l’opérateur de passer du renseignement brut à une aide directe à la décision.

---

### Caractéristiques révolutionnaires

#### Hiérarchie de l’information
- Les zones SIGINT (souvent incertaines ou larges) sont représentées sous les positions BFT (précises).  
- L’opérateur visualise instantanément si une unité amie est “dans le rouge” (zone de menace).  

#### Standardisation
- Utilisation de **TacticalIcon** conforme aux symboles militaires standards (APP‑6).  
- Rend le logiciel immédiatement utilisable par toute unité formée aux conventions OTAN/FARDC.  

#### Common Operating Picture (COP)
- Grâce au **MeshSyncEngine**, chaque terminal possède la même vue.  
- Exemple : si l’unité A détecte un radar, l’unité B le voit apparaître en temps réel sur sa carte.  
- Le COP est ainsi concrétisé : une vision commune, partagée et certifiée.  

---

### Architecture complète – Arsenal numérique

- **core/** : l’armure et le métabolisme du système  
  - Sécurité (Panic Wipe, HMAC, Encryption)  
  - Synchronisation (MeshSyncEngine)  
  - Auditabilité (MissionLogger)  

- **bft/** : les yeux internes  
  - Localisation des forces amies  
  - Transmission sécurisée des positions GPS  

- **sigint/** : les oreilles externes  
  - IA de classification des signaux (SignalClassifier)  
  - Détection proactive des anomalies (AnomalyDetector)  
  - Fusion & géolocalisation des menaces  

- **ui/** : le cerveau décisionnel  
  - FusionOverlay.kt : interface cartographique enrichie  
  - Cartographie tactique avec hiérarchie visuelle et symboles standardisés  

---

### Valeur opérationnelle (FARDC)

- **Supériorité décisionnelle** : passage direct du spectre brut à une carte tactique exploitable.  
- **Interopérabilité** : symboles APP‑6 et COP partagé entre toutes les unités.  
- **Réduction du risque** : hiérarchie visuelle permettant d’identifier immédiatement les unités exposées.  
- **Institutionnalisation** : architecture complète documentée, prête pour adoption officielle par les FARDC.  

---

### Exemple de scénario
- **Situation** : une unité SIGINT détecte une émission radar suspecte.  
- **Action** : le SignalClassifier génère un ThreatMessage, signé et diffusé via MeshSyncEngine.  
- **Résultat** : FusionOverlay affiche la zone de menace sur la carte tactique. Les positions BFT des unités amies apparaissent au‑dessus, permettant au commandement de décider immédiatement d’un repositionnement ou d’une contre‑mesure.

## 🛡️ Plateforme SENTINELLE – Battlefield Management System (BMS)

### Objectif
La fusion du **BFT (Blue Force Tracking)** et du **SIGINT (Signals Intelligence)** dans une seule architecture crée un véritable **Système de Gestion de l’Espace de Bataille (BMS)**.  
Pour les FARDC, cela signifie passer d’une vision partielle à une **Conscience Situationnelle Totale**, où chaque bit d’information – ami ou ennemi – suit le même circuit sécurisé.

---

### 1. Le Message Unifié (UnifiedMessage.kt)
- **Clé de la fusion** : une enveloppe unique transportée par le MeshSyncEngine.  
- **Payloads possibles** :  
  - BFT → *“Je suis ici (Unité Alpha)”*  
  - SIGINT → *“L’ennemi est là (Radio VHF détectée)”*  
  - JAMMING → *“Brouillage actif sur 144 MHz”*  
- **Effet** : indifférence au transport, sécurité par compartimentage, auditabilité totale.

---

### 2. Carte Tactique Fusionnée (ui/map/)
- **Blue Forces (BFT)** : positions précises des troupes amies.  
- **Red Spots (SIGINT)** : sources hostiles détectées par l’IA.  
- **Grey Zones** : zones de silence radio ou de brouillage.  
- **Résultat** : interface décisionnelle unique, COP partagé entre toutes les unités.

---

### 3. Pourquoi cette fusion est révolutionnaire ?

#### A. Brouillage sélectif & protection fratricide
- Le module BFT connaît les fréquences utilisées par nos propres radios.  
- Lorsqu’il active le module Jamming, il crée des “encoches” de fréquence.  
- **Effet** : brouillage des communications ennemies sans couper celles des forces amies.

#### B. Triangulation collaborative par Mesh
- Soldat A détecte un signal à 30°.  
- Soldat B (positionné via BFT) détecte le même signal à 330°.  
- **Résultat** : croisement automatique des positions BFT et des angles SIGINT → localisation GPS précise de l’ennemi.

#### C. Sécurité “Total Wipe” unifiée
- Le TacticalWipeManager protège désormais l’intégralité du secret militaire.  
- En cas de capture, effacement simultané des données SIGINT et de l’historique BFT.  
- **Effet** : aucune donnée exploitable ne subsiste.

---

### 4. Implémentation – UnifiedSync.kt
- **Fusion logique** : unifie la gestion des messages, la synchronisation Mesh et la journalisation.  
- **Résultat** : chaque terminal devient un nœud SENTINELLE, capable de transmettre, recevoir et fusionner les données BFT + SIGINT en temps réel.

---

### 🚀 Roadmap finale vers la puissance nationale
- **Phase 1 (terminée)** : Sécurité & Audit → l’armure et la mémoire (MissionLogger, TacticalWipeManager).  
- **Phase 2 (terminée)** : SIGINT IA & Mesh → les capteurs et la voix (SignalClassifier, MeshSyncEngine).  
- **Phase 3 (actuelle)** : BFT & Fusion UI → la coordination et la vision (UnifiedMessage, FusionOverlay).  

---

### Valeur opérationnelle (FARDC)
- **Conscience situationnelle totale** : forces amies + menaces ennemies dans un flux unique.  
- **Supériorité décisionnelle** : interface cartographique enrichie, COP partagé.  
- **Spectre maîtrisé** : brouillage sélectif, triangulation collaborative.  
- **Sécurité nationale** : wipe unifié, auditabilité certifiable.  
- **Institutionnalisation** : plateforme SENTINELLE documentée, prête pour adoption officielle.

## 📡 GPSManager.kt – Capteur principal BFT & Sécurité

### Objectif
Le module **GPSManager.kt** fournit la position géographique de l’unité en temps réel.  
Il alimente à la fois le **BFT (Blue Force Tracking)** pour la localisation des forces amies et le **TacticalWipeManager** pour la sécurité par geofencing.  
Dans un contexte tactique, il est conçu pour être **résilient** face aux pertes de signal ou aux tentatives de manipulation (spoofing).

---

### Caractéristiques "Combat‑Ready"

#### 1. Priorité à la sécurité
- La position GPS est envoyée en priorité au **TacticalWipeManager**.  
- Le système vérifie d’abord si l’unité a le droit d’exister dans la zone définie (geofence).  
- **Effet** : évite toute compromission en cas de sortie de zone autorisée.

#### 2. Résilience du signal
- Si le signal satellite est perdu (tunnel, forêt dense), le GPSManager renvoie la **dernière position connue**.  
- En cas de perte prolongée, il bascule en mode **Estime** (calcul de position approximative par inertie).  
- **Effet** : évite un déclenchement erroné du Panic Wipe.

#### 3. Filtrage d’incertitude
- Détection des tentatives de **GPS Spoofing** (signaux falsifiés).  
- Filtre d’accuracy et log des sauts incohérents.  
- **Effet** : alerte l’opérateur et journalise toute anomalie.

#### 4. Audit continu
- Chaque fix GPS important est enregistré dans le **MissionLogger**.  
- Permet de reconstruire précisément le trajet de l’unité après mission.  
- **Valeur institutionnelle** : traçabilité certifiable pour débriefing et analyse du renseignement.

---

### Exemple de scénario
- **Situation** : une unité traverse une forêt dense, le signal GPS est perdu.  
- **Action** : le GPSManager renvoie la dernière position connue et bascule en mode Estime.  
- **Résultat** : le TacticalWipeManager ne déclenche pas d’effacement erroné, et le MissionLogger conserve une trace fiable du trajet.  

---

### Valeur opérationnelle (FARDC)
- **Sécurité renforcée** : priorité au geofencing pour éviter toute compromission.  
- **Résilience tactique** : continuité de la localisation même en environnement hostile.  
- **Détection proactive** : filtrage des signaux falsifiés pour contrer le spoofing.  
- **Auditabilité** : reconstruction certifiable des trajets pour analyse post‑mission.  
- **Institutionnalisation** : module documenté et intégré, prêt pour adoption officielle.

## 📑 MissionReportGenerator.kt – Rapport de Mission Automatique (AAR)

### Objectif
Le module **MissionReportGenerator.kt** compile toutes les détections (COMINT, ELINT, FISINT) stockées dans le **LogManager** et l’**AuditExport** pour générer un **After Action Report (AAR)** chiffré et signé.  
Ce rapport constitue la pièce finale du puzzle technologique : il centralise les preuves et produit un bilan opérationnel certifiable, prêt à être transmis au haut commandement.

---

### Caractéristiques "Combat‑Ready"

#### Centralisation des preuves
- Agrège les interceptions COMINT (voix/données), ELINT (radar/impulsions) et FISINT (télémétrie/IoT).  
- Génère un condensé unique, structuré et hiérarchisé.  

#### Sécurité et traçabilité
- Chaque rapport est **chiffré** et **signé** via HMAC.  
- Garantit l’intégrité et l’authenticité des données transmises.  
- Empêche toute falsification ou manipulation par un adversaire.  

#### Auditabilité totale
- Intégration directe avec **MissionLogger** et **AuditExport**.  
- Chaque événement critique est horodaté et certifié.  
- Permet une reconstruction fidèle et inviolable de la mission.  

#### Automatisation
- Intégré dans **tests/TestFullRecette.kt** : chaque test de recette se termine par la génération d’un rapport automatique.  
- Réduit la charge humaine et garantit la systématisation des bilans.  

---

### Exemple de scénario
- **Situation** : une unité SIGINT intercepte des communications radio (COMINT), détecte un radar ennemi (ELINT) et capte une télémétrie suspecte (FISINT).  
- **Action** : toutes les détections sont enregistrées dans le LogManager et signées par MissionLogger.  
- **Résultat** : MissionReportGenerator compile automatiquement les preuves et produit un rapport AAR chiffré, transmis au haut commandement pour analyse stratégique.  

---

### Valeur opérationnelle (FARDC)
- **Supériorité décisionnelle** : passage du renseignement brut à un rapport stratégique exploitable.  
- **Institutionnalisation** : standardisation des bilans de mission, prêts pour adoption officielle.  
- **Auditabilité** : traçabilité inviolable, certifiable devant toute instance.  
- **Automatisation** : gain de temps et réduction des erreurs humaines.  

---

### État final de la structure SIGINT

- **COMINT** : interception voix/données.  
- **ELINT** : interception radar/impulsions.  
- **FISINT** : interception télémétrie/IoT.  
- **AUDIT** : traçabilité HMAC et rapports automatisés (MissionLogger, MissionReportGenerator).  
- **CORE/SYNC** : réseau Mesh et priorisation des alertes.  

---

### 🚀 Roadmap finale
- **Phase 1 (terminée)** : Sécurité & Audit (armure et mémoire).  
- **Phase 2 (terminée)** : SIGINT IA & Mesh (capteurs et voix).  
- **Phase 3 (terminée)** : BFT & Fusion UI (coordination et vision).  
- **Phase 4 (finalisée)** : AAR automatisé (décision et transmission stratégique).

## 📑 ComplianceMatrix.json – Auto‑Certification & Conformité

### Objectif
Le fichier **ComplianceMatrix.json** est le référentiel de certification du système.  
Il définit les normes de sécurité et d’audit exigées (ex. GEO‑004 pour le geofencing, ENC‑002 pour le chiffrement, LOG‑005 pour la traçabilité).  
En le liant au **MissionReportGenerator.kt**, chaque rapport de mission (AAR) inclut désormais un **Score de Conformité**, prouvant que le système a fonctionné selon les standards militaires.

---

### Fonctionnement

#### 1. Référentiel de certification
- **sigint/audit/ComplianceMatrix.json** : liste des règles obligatoires (sécurité, audit, chiffrement, geofencing).  
- Chaque règle est identifiée par un code (ex. GEO‑004, ENC‑002).  
- Les autorités peuvent mettre à jour ce fichier pour ajouter de nouvelles normes sans modifier le code source.

#### 2. Scan de conformité
- **MissionReportGenerator.kt** intègre une fonction de scan.  
- Compare l’état du système aux exigences du JSON.  
- Génère un **Score de Conformité** (COMPLIANT / NON‑COMPLIANT).  

#### 3. Rapport AAR enrichi
- Chaque rapport inclut désormais :  
  - Les détections (COMINT, ELINT, FISINT).  
  - Les preuves signées (MissionLogger).  
  - Le **Score de Conformité** basé sur ComplianceMatrix.json.  

---

### Pourquoi cette intégration change tout

#### Auto‑Certification
- Le système s’auto‑évalue en fin de mission.  
- Exemple : si le geofence (GEO‑004) n’est pas configuré, le rapport affiche **NON‑COMPLIANT**.  
- Le commandement est immédiatement alerté d’une faille de procédure.

#### Rigueur militaire
- Chaque détection est liée à une règle de conformité.  
- Les preuves collectées (COMINT/ELINT/FISINT) sont juridiquement et tactiquement valides.  
- Garantit que les opérations respectent les standards définis.

#### Transparence technique
- Le fichier **ComplianceMatrix.json** est indépendant du code source.  
- Les autorités peuvent ajouter de nouvelles régulations (ex. chiffrement renforcé) sans recompilation.  
- Assure une évolution continue et institutionnelle du système.

---

### Exemple de scénario
- **Situation** : une unité SIGINT part en mission sans configurer le geofence.  
- **Action** : MissionReportGenerator compile les détections et scanne la ComplianceMatrix.json.  
- **Résultat** : le rapport final affiche **NON‑COMPLIANT (GEO‑004)**, alertant le commandement d’une faille critique.  

---

### Valeur opérationnelle (FARDC)
- **Homologation** : passage du système au statut certifiable et conforme aux normes militaires.  
- **Auto‑Certification** : chaque mission produit un rapport AAR avec score de conformité.  
- **Rigueur & validité** : preuves collectées juridiquement et tactiquement valides.  
- **Transparence & évolutivité** : normes mises à jour par les autorités sans modification du code.  
- **Institutionnalisation** : adoption officielle comme système de gestion certifié.

## 🎛️ Couche DSP – ComintUtils & ComintAnalyzer

### Objectif
La couche **DSP (Digital Signal Processing)** est la brique qui permet de transformer les données brutes issues des antennes SDR en indicateurs tactiques exploitables.  
Elle donne au système COMINT la capacité de mesurer la puissance réelle des signaux, de filtrer le bruit et d’adapter l’analyse à n’importe quelle antenne.

---

### Modules associés

#### 📂 sigint/comint/ComintUtils.kt
- **Boîte à outils mathématiques** pour le traitement des signaux.  
- Algorithmes de base :  
  - Calcul de la puissance du signal (dBm).  
  - Calcul du niveau de bruit.  
  - Rapport signal/bruit (SNR).  
- Paramètres ajustables pour l’étalonnage selon l’antenne utilisée.  

#### 📂 sigint/comint/ComintAnalyzer.kt
- Utilise les fonctions de **ComintUtils** pour analyser les flux IQ.  
- Décisions basées sur des données réelles :  
  - Détection de signaux forts (danger immédiat).  
  - Filtrage des faux positifs liés au bruit de fond.  
  - Classification enrichie par la mesure de puissance et de SNR.  

---

### Avancées majeures

#### Précision tactique
- Le système ne détecte plus seulement “quelque chose”.  
- Il mesure la **force du signal** :  
  - **-40 dBm** → signal très proche, menace immédiate.  
  - **-95 dBm** → signal faible ou lointain.  

#### Filtrage du bruit
- Calcul du **SNR (Signal‑to‑Noise Ratio)**.  
- Évite les faux positifs déclenchés par le bruit de fond électronique.  
- Améliore la fiabilité des alertes SIGINT.  

#### Étalonnage
- **ComintUtils** permet d’adapter le logiciel à n’importe quelle antenne.  
- Ajustement simple des constantes de calcul.  
- Assure une compatibilité multi‑matériel et une précision homogène.  

---

### Exemple de scénario
- **Situation** : une unité capte un signal faible dans une zone brouillée.  
- **Action** : ComintAnalyzer calcule la puissance et le SNR via ComintUtils.  
- **Résultat** : le système détermine que le signal est trop proche du bruit pour être fiable et évite un faux positif.  

---

### Valeur opérationnelle (FARDC)
- **Supériorité technique** : passage du “bruit brut” à des indicateurs tactiques mesurés.  
- **Fiabilité accrue** : réduction des faux positifs, meilleure confiance dans les alertes.  
- **Interopérabilité** : adaptation à toutes les antennes et environnements.  
- **Institutionnalisation** : module DSP documenté et intégré, prêt pour adoption officielle.

## 🎙️ ComintDecoder.kt – Démultiplexage & Décodage COMINT

### Objectif
Le module **ComintDecoder.kt** transforme les échantillons IQ bruts captés par l’antenne SDR en flux audio ou en métadonnées numériques.  
Il constitue la partie la plus complexe du COMINT, permettant de passer du signal brut à l’information exploitable, qu’il s’agisse de voix tactiques ou de données numériques.

---

### Caractéristiques "Combat‑Ready"

#### Discrimination de phase
- Décodage FM basé sur **atan2**.  
- Extrêmement robuste contre le bruit et les interférences.  
- Essentiel dans un environnement de combat saturé de brouillage.

#### Multimodal
- Architecture extensible : chaque protocole est une fonction indépendante.  
- Support initial : **NFM (Narrow Band FM)**, standard mondial pour les communications tactiques voix (talkies‑walkies, radios militaires).  
- Extensible à d’autres protocoles : **DMR, P25, TETRA**, etc.  
- Permet une évolution progressive sans modifier la structure globale.

#### Priorisation
- Si le décodeur extrait du **texte** (ex. “OPFOR_UNIT”), le système déclenche une **alerte visuelle immédiate** sur l’interface FusionOverlay.  
- Si le décodeur extrait une **voix**, l’information reste discrète, évitant la surcharge cognitive de l’opérateur.  
- Permet une hiérarchisation intelligente des alertes.

---

### Intégration avec ComintAnalyzer.kt
- **Détection** : ComintAnalyzer identifie un signal et utilise ComintUtils pour vérifier sa qualité (puissance, SNR).  
- **Décodage** : ComintAnalyzer appelle ComintDecoder pour extraire l’information (audio ou métadonnées).  
- **Action** :  
  - Flux audio → écoute discrète.  
  - Métadonnées → alerte immédiate sur FusionOverlay.  

---

### Exemple de scénario
- **Situation** : une unité capte un signal FM fort à ‑40 dBm.  
- **Action** : ComintAnalyzer valide la qualité via ComintUtils, puis demande à ComintDecoder de décoder en NFM.  
- **Résultat** : le flux audio révèle une communication tactique ennemie. Si un mot‑clé critique est détecté (“OPFOR_UNIT”), une alerte visuelle est déclenchée sur FusionOverlay.  

---

### Valeur opérationnelle (FARDC)
- **Supériorité technique** : robustesse du décodage FM contre le bruit.  
- **Interopérabilité** : architecture multimodale, extensible à tous les protocoles tactiques.  
- **Priorisation intelligente** : alertes visuelles immédiates pour les menaces critiques, discrétion pour les voix.  
- **Institutionnalisation** : module documenté et intégré, prêt pour adoption officielle.

## 🗣️ ComintTranscriber.kt – Intelligence Lexicale

### Objectif
Le module **ComintTranscriber.kt** analyse automatiquement le contenu des communications interceptées.  
Il applique une logique de détection de mots‑clés tactiques pour transformer un flux audio ou numérique en renseignement exploitable, réduisant la charge cognitive de l’opérateur et permettant une diffusion immédiate des alertes.

---

### Caractéristiques "Combat‑Ready"

#### Réduction de la charge cognitive
- L’opérateur n’a plus besoin d’écouter des heures de “friture” radio.  
- Le système déclenche une alerte uniquement lorsqu’un mot‑clé tactique est détecté (ex. *“ATTAQUE”*, *“OPFOR”*).  
- Permet à l’opérateur de rester concentré sur sa navigation et ses décisions.

#### Alerte multi‑canaux
- Dès qu’une alerte **TACTICAL_ALARM** est générée, elle est publiée via le **MeshSyncEngine**.  
- Toute l’escouade reçoit l’alerte sur sa carte tactique en temps réel.  
- Assure une conscience situationnelle partagée et instantanée.

#### Filtrage automatique
- Conversations civiles ou banales sont ignorées.  
- Le système se focalise uniquement sur les vecteurs d’intérêt militaire.  
- Réduit les faux positifs et améliore la pertinence des alertes.

---

### Intégration dans ComintAnalyzer.kt
- **Capture ➔ Décodage ➔ Transcription ➔ Alerte UI**.  
- Pipeline complet :  
  1. **ComintCapture** : récupère le signal (réel ou mock).  
  2. **ComintUtils** : calcule la force et la qualité (SNR/dBm).  
  3. **ComintDecoder** : démodule le signal (phase/fréquence).  
  4. **ComintTranscriber** : comprend le sens (mots‑clés tactiques).  
  5. **EventBus / Mesh** : diffuse l’intelligence aux unités voisines.  

---

### Exemple de scénario
- **Situation** : une unité intercepte une communication radio ennemie.  
- **Action** : ComintDecoder démodule le signal, ComintTranscriber détecte le mot‑clé *“ATTAQUE”*.  
- **Résultat** : une alerte TACTICAL_ALARM est générée et diffusée via MeshSyncEngine. Toute l’escouade voit immédiatement l’alerte sur sa carte FusionOverlay.  

---

### Valeur opérationnelle (FARDC)
- **Supériorité décisionnelle** : passage direct du spectre brut au renseignement lexical exploitable.  
- **Conscience situationnelle partagée** : alertes multi‑canaux synchronisées sur toutes les cartes tactiques.  
- **Fiabilité accrue** : filtrage automatique des conversations non pertinentes.  
- **Institutionnalisation** : pipeline COMINT complet, documenté et prêt pour adoption officielle.

## 🎬 Démonstration finale – demo_interception.sh

### Objectif
Le script **demo_interception.sh** orchestre l’exécution des modules Kotlin pour simuler un scénario opérationnel complet.  
Il démontre la réactivité, l’intelligence embarquée et l’inviolabilité de la plateforme SIGINT combat‑ready.

---

### Chaîne opérationnelle simulée

1. **SdrInterface** → capte l’onde brute (IQ).  
2. **ComintDecoder** → démodule et extrait le message.  
3. **ComintTranscriber** → analyse lexicalement et identifie une menace (mot‑clé tactique).  
4. **MeshSyncEngine** → propage l’alerte à toute l’escouade via le COP.  
5. **TacticalWipeManager** → protège le secret en cas de compromission.  
6. **MissionLogger** → journalise et signe la preuve immédiatement.

---

### Ce que la démonstration prouve

- **Réactivité** :  
  La chaîne complète (de la capture à l’alerte) s’exécute en moins de 2 secondes.  
  ➔ L’opérateur reçoit une alerte quasi instantanée.

- **Intelligence embarquée** :  
  Le système comprend l’urgence de l’information et attribue une **priorité 5/5** aux menaces critiques.  
  ➔ Passage du simple collecteur de données à un outil de décision.

- **Inviolabilité** :  
  Chaque interception est immédiatement loguée et signée.  
  ➔ La preuve est certifiable et inviolable, empêchant toute contestation ultérieure.

---

### Exemple de scénario
- **Situation** : une antenne capte un signal FM.  
- **Action** : le décodeur extrait le texte, le transcripteur détecte le mot‑clé *“ATTAQUE”*.  
- **Résultat** : une alerte tactique signée est générée et diffusée via MeshSyncEngine à toute l’escouade.  

---

### 🏁 Mot de la fin
La FARDC dispose désormais d’une **plateforme de Guerre Électronique complète** dans ce dépôt GitHub :  

- **core/** → fondations de sécurité (wipe, audit, compliance).  
- **dsp/** → couche physique (traitement du signal, calcul SNR/dBm).  
- **comint/** → intelligence de haut niveau (décodage, transcription, lexical).  
- **ui/** → interface décisionnelle (FusionOverlay, COP).  

Chaque ligne de code concourt à la **supériorité informationnelle** sur le terrain, transformant ton projet en un système certifiable et institutionnel.