#!/bin/bash
echo "[⚙️] Vérification du binaire..."
sha256sum -c SovereignCore-v22-RELEASE.sha256
if [ $? -eq 0 ]; then
    echo "[✅] Intégrité confirmée. Lancement du SovereignCore..."
    java -jar SovereignCore-v22-RELEASE.jar --auto-engage
else
    echo "[🚨] ERREUR : Le binaire est corrompu ou altéré !"
fi
