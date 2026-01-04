# 🛡️ PROTOCOLE D'INVULNÉRABILITÉ SOUVERAINE (v5.2)
**Classification :** GRADE MILITAIRE - CONFIDENTIEL
**Actif protégé :** SOVEREIGN-CORE-PSC
**Objectif :** Résilience absolue, Anti-Forensics et Protection de la Propriété Intellectuelle.

---

## 1. ARCHITECTURE DE DÉFENSE (L'OIGNON)
Le système repose sur trois couches de sécurité concentriques :

### A. Couche Matérielle (Hardware Binding)
Le logiciel est verrouillé sur l'ID unique (UUID) du processeur hôte via le `SecurityController`. 
* **Effet :** Toute copie sur un matériel non autorisé déclenche un arrêt immédiat avant le chargement des modules SIGINT.
* **Vérification :** Empreinte Maître `C7B6B762`.

### B. Couche Logicielle (Intégrité GPG)
Chaque binaire (`.jar`) doit être accompagné de sa signature `.asc`.
* **Règle :** Le `SecurityController` vérifie la présence et la validité de la signature au démarrage.
* **Risque contré :** Injection de code malveillant ou "backdoor" par un tiers.

### C. Couche de Persistance (Anti-Forensics)
Utilisation du module `AntiForensics` pour le nettoyage des traces.
* **Protocole de Wipe :** Réécriture binaire aléatoire (Standard DoD 5220.22-M).
* **RAM Stealth :** Purge des variables sensibles après chaque opération critique pour empêcher le dump de mémoire vive.

---

## 2. PROCÉDURES D'URGENCE (PANIC PROTOCOLS)

### ⚠️ Protocole "ROUGE" (Compromission Détectée)
Déclenché automatiquement si :
1. Le hachage matériel ne correspond pas.
2. Le fichier de signature GPG est manquant ou corrompu.
**Action :** Exécution de `secureWipe()` sur le répertoire `/data/` et suicide du processus.

### ⚠️ Protocole "NOIR" (Saisie Physique Imminente)
Action manuelle de l'opérateur via la commande `panic` :
* Effacement instantané des clés de chiffrement.
* Destruction des logs de mission.
* Désinstallation furtive du noyau (`Self-Destruct`).

---

## 3. MAINTENANCE DE LA VALEUR (STRATÉGIE FINANCIÈRE)
Pour maintenir la valorisation à **1.35M $**, l'opérateur doit :
1. **Rotation des Clés :** Changer les clés de chiffrement tous les 30 jours via `rotate_keys.sh`.
2. **Audit de Signature :** Ne jamais exécuter un build non signé manuellement.
3. **Isolation Cloud :** Ne téléverser que des archives ZIP chiffrées (AES-256) avec mot de passe complexe (ex: `Cbl1623$$`).

---

## 4. COMMANDES DE CONTRÔLE TACTIQUE
| Commande | Action | Résultat |
| :--- | :--- | :--- |
| `./combat-ready.sh --check` | Audit d'intégrité | Rapport de santé |
| `./harden_binary.sh` | Polymorphisme | Nouvelle signature binaire |
| `./clean_logs.sh --secure` | Anti-Forensics | Effacement DoD des logs |

---
**SOUVERAINETÉ ET DISCRÉTION.**
*Émis par le Commandement SIGINT - FARDC*
