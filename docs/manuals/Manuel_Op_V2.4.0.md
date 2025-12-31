
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

