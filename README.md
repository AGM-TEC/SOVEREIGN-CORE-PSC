# 🛡️ SOVEREIGN CORE v4.0 (SIGINT/EW)
## Système Souverain de Surveillance et de Guerre Électronique

Le **Sovereign Core v4.0** est un noyau de renseignement électronique (SIGINT) conçu pour l'interception, l'analyse et la neutralisation des menaces électromagnétiques.

### 🚀 Capacités Clés
* **Native SDR Engine** : Pilotage direct des périphériques radio via JNI.
* **SignalStreamer HUD** : Visualisation spectrale en temps réel via WebSockets.
* **MeshSyncEngine** : Synchronisation sécurisée (AES-256) entre unités tactiques.
* **SignalClassifier** : Identification automatique des signatures de drones (DJI, Autel).

### 📂 Structure du Projet
* `/src` : Noyau Kotlin (Main, Security, Network).
* `/ui` : Interface HUD tactique pour l'opérateur.
* `/docs` : Manuel de l'Opérateur et doctrines d'engagement.
* `/scripts` : Outils de simulation et de diagnostic.

### 🛠️ Déploiement
\`\`\`bash
java -cp "bin/SovereignCore.jar:libs/*" com.fardc.sigint.core.MainKt
\`\`\`

---
*Développé pour la souveraineté technologique des forces de défense.*
