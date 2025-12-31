
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

