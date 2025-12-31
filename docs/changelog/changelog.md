
# SOVEREIGN-CORE v2.3.0 – Full Offensive Suite (SIGINT)

Cette version marque le passage à une architecture modulaire complète pour les opérations de renseignement électronique.

## 🔧 Modules Noyau (Core)
- **SecurityVault** : Chiffrement AES-256-GCM avec vérification d'intégrité.
- **MissionReporter** : Génération automatique de rapports tactiques avec métadonnées techniques.
- **DataExfiltrator** : Tunnel sécurisé pour l’exfiltration vers le centre de commandement.

## ⚡ Vecteurs d'Interception (Offensive)
- **MPesaCommander** : Interception des flux financiers mobiles (RDC).
- **PagoMovilHandler** : Interface pour les systèmes de paiement mobile internationaux.
- **UniversalPhish & PhishCommander** : Déploiement de vecteurs d’ingénierie sociale multi-cibles.

## 🛠️ Améliorations Techniques
- **Optimisation ARM64** : Build robuste pour Termux et environnements mobiles.
- **Gestion Dynamique** : Boot avec vérification d’état (FORCE-REPORT).
- **Classpath Explicite** : Compilation Kotlin sans dépendance Gradle, pour performance maximale.
- **LogicBomb** : Nettoyage sécurisé sans destruction du projet.
- **BruteForceDashboard** : Monitoring en temps réel avec export JSON.
- **CommandInterface** : CLI robuste avec validation et sortie propre.
- **CyberFinCapacity** : Interdiction réseau + surveillance FinCap avec auditabilité.

## 📦 Artefacts
- `sigint-core-all.jar` compilé avec `kotlinc` et classpath explicite.
- Compatible avec Termux, Codespaces, et environnements souverains.

## 🛡️ Sécurité
- Modules critiques désactivables via configuration.
- Aucun accès externe non contrôlé.