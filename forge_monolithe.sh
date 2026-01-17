#!/data/data/com.termux/files/usr/bin/bash

echo "[*] --- DÉBUT DE LA FORGE SOUVERAINE ---"

# 1. Re-compilation des binaires UHD si nécessaire
cd uhd/host/build
make rx_samples_to_file -j4
cd ../../../

# 2. Compilation du Monolithe Kotlin
kotlinc SDRController.kt -include-runtime -d Monolithe.jar

if [ 0 -eq 0 ]; then
    echo "[+] Forge réussie : Monolithe.jar est prêt."
    
    # 3. Synchronisation automatique vers GitHub
    echo "[*] Archivage vers GitHub..."
    git add .
    git commit -m "Mise à jour tactique : Forge complète SIGINT v1.0"
    # git push origin main
else
    echo "[!] ÉCHEC DE LA FORGE."
fi
