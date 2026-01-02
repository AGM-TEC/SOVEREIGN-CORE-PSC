# 🛡️ MANUEL DE L'OPÉRATEUR - SOVEREIGN CORE v4.1
## UNITÉ DE LOCALISATION ET GUERRE ÉLECTRONIQUE - FARDC

### 1. NOUVEAUTÉ MAJEURE : MODE FUSION & GÉOLOCALISATION
La v4.1 introduit la capacité de localiser avec précision un émetteur ennemi par triangulation et fusion des données SIGINT.

### 2. ARCHITECTURE DU MODE FUSION
* **Moteur TDOA** : Calcule la position via la différence de temps d'arrivée des signaux.
* **Module Fusion_Geo** : Combine les rapports de plusieurs unités Mesh pour affiner les coordonnées.
* **Triangulation Multi-Unités** : Nécessite au moins 3 récepteurs synchronisés pour une précision optimale.

### 3. PROTOCOLE DE GÉOLOCALISATION TACTIQUE
1. **Établir le Maillage** : Déployer 3 unités Sovereign en formation triangulaire.
2. **Synchronisation** : Activer le MeshSyncEngine pour l'horodatage haute précision.
3. **Capture & Calcul** : Le système fusionne les données automatiquement et projette la position sur le HUD.

### 4. MODES DE COMBAT INTÉGRÉS
* **🟢 SILENT WATCH** : Surveillance passive sans émission.
* **🟠 AUTO DEFENSE** : Riposte automatique sur détection IA.
* **🔴 ELECTRONIC ASSAULT** : Brouillage de barrage haute puissance.
* **🔵 GEO FUSION (NEW)** : Localisation et désignation de cible.

---
*Classification : SECRET DÉFENSE*
