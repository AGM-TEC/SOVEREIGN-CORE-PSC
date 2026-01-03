#!/bin/bash
# 🛡️ SOVEREIGN CORE - DOCTRINE DE COMBAT FARDC v5.1

MANUAL_PATH="MANUEL_MASTER.md"

echo "📝 Rédaction du Manuel de Doctrine Tactique..."

cat << 'MANUAL_EOF' > $MANUAL_PATH
# 📘 MANUEL DE DOCTRINE TACTIQUE - SOVEREIGN CORE v5.1
**Unité :** SIGINT / Guerre Électronique FARDC ⚔️
**Classification :** SECRET DÉFENSE

---

## 📡 SECTION I : MODES DE COMBAT & EXEMPLES OPÉRATIONNELS

### 1. 🟢 SILENT WATCH (Mode Infiltration)
* **Description :** Écoute passive totale. Aucune onde n'est émise par votre antenne.
* **Utilité FARDC :** Détection préventive sans se faire repérer.
* **Exemple de terrain :** Lors d'une patrouille en forêt, le système capte une fréquence de **144.500 MHz**. L'IA l'identifie comme un talkie-walkie civil utilisé par des rebelles. Vous localisez leur position avant qu'ils ne voient votre patrouille.

### 2. 🟠 AUTO-DEFENSE (Mode Sentinelle)
* **Description :** Surveillance automatique avec alerte de menace.
* **Utilité FARDC :** Protection des camps et des bases temporaires.
* **Exemple de terrain :** En pleine nuit, un drone ennemi approche de votre position. Le système calcule un **Threat Score de 85%** et fait vibrer votre terminal. Vous avez 30 secondes pour réagir avant le survol.

### 3. 🔴 ELECTRONIC ASSAULT INTELLIGENT (Mode Neutralisation)
* **Description :** Brouillage adaptatif qui traque les sauts de fréquence (Frequency Hopping).
* **Utilité FARDC :** Rupture immédiate des communications et du contrôle drone ennemi.
* **Exemple de terrain :** Un drone piégé attaque un convoi de ravitaillement. Tapez \`assault\`. Le SDR sature le signal de commande du drone. Même si le drone tente de changer de fréquence, l'IA le suit et maintient le brouillage jusqu'à ce que le drone s'écrase ou retourne à sa base.



---

## 🛡️ SECTION II : SÉCURITÉ ET PROTECTION (BFT)

### 📍 GEOFENCING & ALERTE FRATRICIDE
* **Description :** Corrélation entre la position des alliés et les zones d'émission.
* **Utilité FARDC :** Éviter de brouiller vos propres troupes lors d'un assaut.
* **Exemple de terrain :** Vous lancez un brouillage sur une colline occupée par l'ennemi. Le système détecte qu'une unité FARDC (Alpha-01) est trop proche de la zone d'impact. Une alerte s'affiche : \`⚠️ RISQUE DE BROUILLAGE ALLIÉ\`. Vous pouvez ordonner à Alpha-01 de reculer avant d'activer la pleine puissance.

---

## 💰 SECTION III : GUERRE CYBER-FINANCIÈRE

### 📥 MPESA & PHISH COMMANDER
* **Description :** Interception tactique des flux financiers et des credentials.
* **Utilité FARDC :** Identifier et couper les circuits de financement des groupes armés.
* **Exemple de terrain :** Dans une zone de transit, vous déployez un point d'accès "leurre". Les financiers rebelles s'y connectent pour coordonner des paiements. Vous interceptez les identifiants, permettant de tracer l'origine des fonds et de neutraliser leurs capacités d'achat de munitions.

---

## 🚀 RÉSUMÉ DES COMMANDES RAPIDES
- **\`assault\`** : Neutralisation électronique immédiate (Traque adaptative).
- **\`silent\`** : Retour à la discrétion totale (Invisibilité RF).
- **\`bft-status\`** : Affiche la position de toutes les unités amies connectées au Mesh.

---
**FIN DU MANUEL - FORCE POUR LA DÉFENSE DE LA PATRIE**
MANUAL_EOF

echo "✅ Manuel mis à jour avec succès dans : $MANUAL_PATH"
