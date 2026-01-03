# 🛡️ MANUEL DE DOCTRINE SIGINT - SOVEREIGN CORE v4.2 [ÉDITION ULTRA-DÉTAILLÉE]

## 🏗️ VOLET I : ARCHITECTURE ET INTERFACE (SECTIONS 1 À 5)

### 1. Gestion des Threads Virtuels (Project Loom)
Le noyau SovereignCore exploite la technologie de pointe du JDK 21 pour la gestion de la haute performance sur matériel mobile.
* **Fonctionnement :** Contrairement aux serveurs classiques qui saturent la RAM avec des threads système lourds (1 Mo par thread), nous utilisons les **Virtual Threads**.
* **Capacité :** Jusqu'à 10 000 capteurs Mesh ou flux SDR analysés simultanément sans ralentir l'interface utilisateur.
* **Avantage tactique :** Permet au Core de rester fluide même pendant une attaque par déni de service (DoS) ou un scan intensif du spectre radioélectronique.
* **Paramétrage :** Activé par défaut. Pour forcer la désactivation (mode diagnostic) : `ConcurrencyUtil.useLoom = false`.

### 2. Sécurité du SecurityVault (Chiffrement AES-256)
Le SecurityVault est le coffre-fort cryptographique du système. Aucune donnée sensible (PIN, identifiants, coordonnées GPS) n'est stockée en clair sur la mémoire flash.
* **Mécanisme :** Chiffrement symétrique AES avec une clé dérivée de l'identifiant unique du matériel (HWID).
* **Processus :**
    1. Capture de la donnée brute (ex: PIN M-Pesa).
    2. Chiffrement immédiat en mémoire vive.
    3. Écriture sécurisée dans `logs/mission_data.json`.
* **Résilience :** En cas d'extraction physique de l'appareil par l'ennemi, les logs restent indéchiffrables sans le processeur d'origine.

### 3. Isolation des Processus et Fail-Safe
L'architecture est segmentée en "Services Latents" totalement indépendants.
* **Stabilité :** Si le module SDR subit une erreur matérielle (antenne débranchée), le serveur Web et les modules offensifs restent opérationnels.
* **Segment Web :** Le moteur Javalin gère l'interface indépendamment du moteur de calcul TDOA.
* **Watchdog interne :** Un thread de surveillance haute priorité redémarre automatiquement tout service critique en moins de 500ms en cas de crash, garantissant une disponibilité de 99.9% en opération.

### 4. Interface HUD (Heads-Up Display) Tactique
L'interface de commandement est accessible sur le port 7070. Elle est optimisée pour une utilisation sur tablettes durcies et écrans tactiles de combat.
* **Accès :** Saisir `http://localhost:7070` dans n'importe quel navigateur interne.
* **Composants visuels :**
    * **Waterfall (Cascade) :** Visualisation thermique des ondes radio en temps réel.
    * **Alert Box :** Notifications visuelles rouges pour les menaces critiques (Drones/UAV).
    * **Terminal Tactique :** Permet l'envoi de commandes manuelles simplifiées sans quitter l'interface graphique.

### 5. Visualisation Radar et Cartographie
Le radar du HUD convertit les données brutes du moteur TDOA en informations géospatiales exploitables.
* **Symbologie :**
    * **Points Verts :** Unités alliées synchronisées via le Mesh.
    * **Points Rouges :** Émissions suspectes ou cibles prioritaires détectées par SIGINT.
* **Précision :** Plus le signal est pur (SNR élevé), plus le point sur le radar est fin. Un cercle de probabilité entoure les cibles dont la position est encore en cours de calcul.
* **Zonage :** Possibilité de configurer des "Geofences" qui déclenchent une alerte sonore si un signal inconnu pénètre un périmètre défini.

--------------------------------------------------

## 📡 VOLET II : SIGINT ET CAPTURE AUDIO (SECTIONS 6 À 10)

### 6. Matériel SDR Bridge (Couche Physique)
Le Sovereign Core communique avec l'espace hertzien via le driver `rtl-sdr`.
* **Fréquences cibles :** Optimisé pour le 2.4GHz (Wi-Fi/Drones) et les bandes VHF/UHF (Radios tactiques).
* **Gain Auto-Adaptatif :** Le système ajuste dynamiquement la sensibilité de l'antenne pour éviter la saturation à proximité des tours de télécommunication ennemies.
* **Sécurité matérielle :** En cas de détection de brouillage intensif (Jamming), le Core protège le tuner en basculant en mode protection.

### 7. Balayage Spectral et Détection (rtl_power)
Le système ne se contente pas d'écouter, il cartographie l'activité électromagnétique.
* **Méthode :** Utilisation de l'intégration temporelle pour repérer les signaux faibles cachés sous le bruit de fond.
* **Signatures :** Le Core compare les pics de puissance avec une base de données locale (ex: Signature OcuSync pour les drones DJI).
* **Logs RF :** Chaque pic anormal est enregistré avec son horodatage et sa fréquence précise dans `logs/spectrum_history.csv`.

### 8. Analyseur FFT et Transformation du Signal
Le flux brut (IQ data) est transformé via une **Fast Fourier Transform (FFT)** pour être lisible par l'IA.
* **Précision :** Résolution jusqu'à 1kHz pour distinguer deux radios émettant sur des canaux adjacents.
* **Waterfall (Cascade) :** Les données FFT alimentent le HUD pour afficher l'historique des signaux. Une trace verticale stable indique un émetteur fixe ; une trace pointillée indique un saut de fréquence (FHSS).

### 9. Interception Vocale (Voice-INT)
Capacité de démodulation en temps réel des communications analogiques.
* **Modes supportés :** NFM (Narrowband FM) pour les talkie-walkies et AM pour les communications aéronautiques.
* **Squelch Intelligent :** Le module audio reste muet tant qu'un signal vocal clair n'est pas détecté, évitant la fatigue auditive de l'opérateur due au bruit blanc.
* **Exemple tactique :** Interception des ordres de mouvement des milices locales sur la bande 136-174 MHz.

### 10. Archivage et Streaming Audio SIGINT
Chaque interception vocale est traitée comme une preuve numérique.
* **Format :** Enregistrement en `.wav` mono 16-bit pour une clarté maximale et une faible consommation d'espace.
* **Streaming :** Le flux démodulé est envoyé via WebSocket vers le HUD, permettant à l'officier de renseignement d'écouter à distance via le portail web 7070.
* **Métadonnées :** Chaque fichier audio est lié à une fréquence et, si possible, à une position TDOA estimée.

--------------------------------------------------

## 🎯 VOLET III : GÉOLOCALISATION ET TRAQUE (SECTIONS 11 À 15)

### 11. Géolocalisation par TDOA (Time Difference of Arrival)
Le moteur TDOA est le sommet de la traque électronique du Sovereign Core.
* **Principe :** Le système compare l'heure exacte d'arrivée d'un signal sur plusieurs capteurs Mesh. La micro-différence (en nanosecondes) permet de tracer des hyperboles dont l'intersection désigne la position de l'ennemi.
* **Précision :** Dépend du nombre de nœuds actifs. Avec 3 nœuds, on obtient un verrouillage 2D précis à 15-30 mètres.
* **Usage :** Indispensable pour localiser un tireur d'élite utilisant une radio ou un opérateur de drone caché.

### 12. Analyse de la Puissance du Signal (RSSI Corrélation)
Lorsque le TDOA manque de nœuds, le système bascule sur l'analyse RSSI.
* **Fonctionnement :** Utilise la loi de l'inverse du carré de la distance pour estimer l'éloignement.
* **Calibration :** Le Core compense automatiquement les obstacles (murs, végétation) pour affiner l'estimation.
* **Indicateur HUD :** Un signal supérieur à -40dB indique une proximité immédiate (< 10m), déclenchant une alerte de proximité vibrante sur le terminal.

### 13. Marquage GPS et Cartographie Tactique
Le Core convertit les vecteurs radio en coordonnées décimales.
* **Interface :** Les cibles sont projetées sur la carte `ui/tactical/map.html`.
* **Persistance :** Une fois une cible verrouillée, sa position est mémorisée même si elle cesse d'émettre pendant une courte période (mémoire de trajectoire).
* **Exportation :** Les coordonnées peuvent être extraites au format KML pour être intégrées dans d'autres systèmes de navigation militaire.

### 14. Corrélation de Cibles Multi-Sources
Le système fusionne les identités numériques avec les positions physiques.
* **Fusion de données :** Si un utilisateur est capturé par le module M-Pesa (Offensive Financière) au moment précis où un signal fort est détecté par le TDOA, le Core lie l'identité de l'individu à sa position GPS.
* **Score de confiance :** Une probabilité (en %) est affichée pour chaque corrélation afin d'éviter les erreurs d'identification.

### 15. Détection de Mouvement et Alertes de Secteur
Le Core surveille l'évolution de la position des signaux dans le temps.
* **Vecteurs :** Calcul de la vitesse et de la direction d'approche (ex: un drone se dirigeant vers la base à 40 km/h).
* **Zones d'Exclusion :** L'opérateur peut définir des "secteurs de tir électronique". Toute intrusion radio dans ces zones déclenche une alerte rouge instantanée sur tous les terminaux de l'unité Mesh.

--------------------------------------------------

## 💰 VOLUMEN IV: OFENSIVA FINANCIERA Y CIBER-GUERRA (SECCIONES 16 A 20)

### 16. Intercepción por DNS Spoofing
El módulo ofensivo redirige el tráfico de red de los objetivos hacia el servidor del Core.
* **Mecanismo:** El Core se hace pasar por la puerta de enlace (Gateway) predeterminada, interceptando las consultas DNS de los dispositivos en el rango Wi-Fi.
* **Objetivo:** Forzar a que cualquier intento de acceder a portales bancarios o de pago móvil sea redirigido a la interfaz controlada por el Sovereign Core.
* **Ejecución:** Se activa automáticamente al detectar dispositivos con firmas digitales vinculadas a objetivos de alto valor.

### 17. Portales de Captura (Phishing Táctico)
Interfaces clonadas con precisión milimétrica para engañar al usuario.
* **Servicios Clonados:** M-Pesa (Congo/África) y Pagomóvil/Bancos locales.
* **Funcionamiento:** Cuando el objetivo intenta realizar una transacción, el Core presenta una pantalla de "Error de Conexión" o "Validación de Seguridad" que solicita el ingreso de credenciales.
* **Validación en Tiempo Real:** Los datos ingresados se verifican instantáneamente para asegurar que no sean datos falsos (honeypots).

### 18. Módulo Deep-Decryptor (Análisis de PIN)
Algoritmo de IA dedicado a predecir y validar códigos de seguridad capturados.
* **Análisis de Patrones:** Compara los PIN capturados con bases de datos de vulnerabilidades (fechas históricas, secuencias comunes como 1234, o prefijos telefónicos).
* **Alerta de Éxito:** Si el Deep-Decryptor confirma que el PIN es "Muy Probable", el Core marca la cuenta del objetivo como "COMPROMETIDA" y lista para la exfiltración de fondos o monitoreo.

### 19. Ofuscación y Rotación de MAC (Furtividad)
Para evitar ser detectado por sistemas de seguridad de red enemigos, el Core oculta su identidad física.
* **MAC Dinámica:** El sistema cambia la dirección MAC de la interfaz de red cada vez que inicia una operación ofensiva.
* **Fingerprinting:** Imita las firmas de dispositivos comunes (como teléfonos Samsung o Huawei) para mezclarse con el tráfico civil normal y evitar sospechas.

### 20. Intercepción de Credenciales y Almacenamiento Seguro
Gestión de los datos obtenidos durante la fase de ataque.
* **Captura:** Los datos se guardan en `logs/mission_data.json` bajo una capa extra de cifrado.
* **Notificación:** El HUD emite una alerta silenciosa "🎣 CAPTURA LOGRADA" solo visible para el operador.
* **Uso Ético-Militar:** Estos datos se utilizan exclusivamente para desarticular el financiamiento de milicias y grupos insurgentes identificados.

--------------------------------------------------

## 🦾 VOLET V : FURTIVITÉ ET MODES DE COMBAT (SECTIONS 21 À 25)

### 21. Mode Saut de Fréquence (Stealth Mesh - FHSS)
Le protocole de communication entre les unités utilise le saut de fréquence (Frequency Hopping Spread Spectrum).
* **Mécanisme :** Le Core change de canal de transmission (ex: Canal 1 -> Canal 11 -> Canal 6) toutes les 10 secondes selon une séquence pseudo-aléatoire synchronisée.
* **Avantage tactique :** Rend le réseau Mesh quasiment indétectable par les analyseurs de spectre ennemis. Même si une fréquence est brouillée, le signal saute instantanément sur une autre.

### 22. Mode SILENT (Silence Radio Total)
Utilisé pour l'infiltration en zone hostile sans détection électronique.
* **Actions :** Extinction des balises Wi-Fi/Bluetooth, arrêt du streaming vidéo HUD, et mise en veille du SDR Bridge.
* **Fonctionnement :** Le Core reste en mode passif ("Listen Only"). Il reçoit les ondes mais n'en émet aucune. Les logs sont stockés localement en attendant la fin de la période de silence.

### 23. Mode AUTO-DEFENSE (Réaction Automatisée)
Le système protège l'unité contre les menaces aériennes et les intrusions.
* **Déclencheur :** Identification d'une signature de drone (DJI, Autel, Parrot) à moins de 150 mètres.
* **Réponse :** Activation immédiate du script `anti_detection.sh` et tentative de saturation du canal de commande du drone pour provoquer un "Return to Home" ou un atterrissage d'urgence.

### 24. Mode ASSAULT (Guerre Électronique Totale)
Le mode d'engagement maximal pour neutraliser une zone.
* **Actions offensives :** Activation simultanée du DNS Spoofing massif, injection de paquets de désauthentification Wi-Fi, et émission de signaux "Ghost" pour saturer les radars ennemis.
* **Risque :** Ce mode est très bruyant électroniquement. Il trahit la position de l'unité mais paralyse totalement les communications adverses.

### 25. Protocole MeshSyncEngine (Intelligence Collective)
Le cerveau partagé du réseau Sovereign.
* **Synchronisation :** Toutes les données de cibles, les interceptions et les alertes sont partagées en temps réel entre les terminaux de l'unité.
* **Résilience :** En cas de destruction ou de capture du terminal "Master", un autre terminal prend automatiquement le relais pour maintenir la coordination du réseau.

--------------------------------------------------

## 🧠 VOLET VI : ANALYSE IA ET SURVEILLANCE AÉRIENNE (SECTIONS 26 À 30)

### 26. Détection de Signatures de Drones (UAV/UAS)
Le Core utilise des modèles d'apprentissage léger pour identifier les protocoles de communication des drones commerciaux et tactiques.
* **Protocoles identifiés :** DJI OcuSync, Lightbridge, et transmissions analogiques 5.8GHz.
* **Alerte Précoce :** Capacité de détecter la mise sous tension d'une télécommande de drone avant même le décollage, offrant un avantage de surprise à l'unité de terrain.

### 27. Radio Fingerprinting (Empreinte Matérielle)
Chaque émetteur radio possède des imperfections microscopiques uniques lors de la modulation.
* **Identification Unique :** Le Core peut distinguer deux radios de même marque et modèle.
* **Suivi de Cible :** Si une cible change de canal ou de fréquence, l'IA la reconnaît par sa signature RF unique, empêchant toute tentative de fuite par "évasion fréquentielle".

### 28. Analyse de Trafic et Métadonnées
Même sans décrypter le contenu, le Core analyse la structure des échanges.
* **Analyse de Flux :** Une augmentation soudaine du trafic radio dans un secteur précis est interprétée comme un signe précurseur d'attaque ou de mouvement de troupes.
* **Hiérarchie :** Identification automatique du "Nœud Maître" (le commandant ennemi) en analysant qui émet le plus et qui reçoit les réponses.

### 29. Corrélation Identité/Position (Fusion SIGINT-GEO)
C'est le sommet du renseignement opérationnel.
* **Processus :** Le système croise les données du module d'Interception Financière (Section 17) avec les coordonnées TDOA (Section 11).
* **Résultat :** "L'utilisateur 'Cible_01' qui vient d'entrer son PIN est localisé avec 95% de certitude à l'angle Nord du bâtiment B."

### 30. Filtrage Anti-Bruit IA (DSP Avancé)
Nettoyage des signaux pour une clarté optimale.
* **Suppression de Parasites :** Élimine le bruit de fond des moteurs ou des générateurs électriques pour isoler uniquement les voix ou les signaux de données.
* **Clarté Vocale :** Utilisation d'un dé-bruiteur neuronal pour rendre audibles les communications radio de très faible qualité interceptées à longue distance.

--------------------------------------------------

## 🔧 VOLET VII : MAINTENANCE ET PROTOCOLES DE SURVIE (SECTIONS 31 À 36)

### 31. Cycle de Mise à Jour et Maintenance GitHub
Le Sovereign Core est un système évolutif. La synchronisation avec le dépôt central est vitale.
* **Procédure :** Utiliser `git pull origin main` pour intégrer les nouveaux algorithmes de décryptage et les patchs de sécurité.
* **OTA (Over-The-Air) :** En cas d'opération prolongée, les mises à jour peuvent être diffusées via le Mesh entre les terminaux sans accès internet.

### 32. Certification d'Intégrité (Hash SHA-256)
La confiance dans le code est la base de la sécurité SIGINT.
* **Vérification :** Chaque binaire `SovereignCore.jar` possède une signature unique. L'opérateur doit comparer le hash local avec celui fourni par le commandement central.
* **Alerte :** Tout écart de signature indique une altération du code (malware, injection ennemie) et doit entraîner le blocage immédiat du terminal.

### 33. Gestion des Conflits de Synchronisation
Sur le terrain, les versions peuvent diverger.
* **Règle d'or :** Toujours privilégier la version du "Master" de l'unité.
* **Méthode technique :** Utilisation du `git stash` pour sauvegarder les données de mission locales avant de forcer la mise à jour du noyau central.

### 34. Protocole de Vérification Post-Déploiement
Avant chaque engagement, l'opérateur suit la "Checklist Alpha" :
1. Alimentation antenne SDR : OK.
2. Port 7070 accessible sur le HUD : OK.
3. Synchronisation Mesh (nb de nœuds) : OK.
4. Espace disque pour les logs audio : suffisant.

### 35. Certification de Stabilité v4.2 [STABLE-ARME]
Cette version est certifiée pour une utilisation en conditions extrêmes (chaleur, humidité, zones de brouillage dense).
* **Limites :** Le système est optimisé pour des sessions de 48h continues. Un redémarrage préventif est conseillé au-delà pour vider les caches de l'analyseur spectral.

### 36. Procédure d'Urgence : Protocole "WIPE" (Destruction)
La sécurité ultime. Si la capture du terminal est imminente :
* **Action manuelle :** Exécuter le script `scripts/finalize_system.sh --wipe`.
* **Conséquences :** Suppression immédiate de `logs/`, destruction des clés du `SecurityVault`, et effacement du binaire `SovereignCore.jar`.
* **Résultat :** L'ennemi récupère un matériel vide de tout renseignement et de tout code source.

--------------------------------------------------
**FIN DU MANUEL DE DOCTRINE SIGINT v10.0**
**GLOIRE À LA PATRIE - SOVEREIGN CORE OPÉRATIONNEL**
