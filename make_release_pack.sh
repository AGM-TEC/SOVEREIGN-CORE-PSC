#!/bin/bash
# ----------------------------------------------------------------
# 🇨🇩 SOVEREIGN-CORE v7.1-GOLD | SECURE DISTRIBUTION PROTOCOL
# ----------------------------------------------------------------

REL_DIR="release_final_gold"
EXPORT_NAME="SOVEREIGN_V7_GOLD_FARDC"

echo "[📦] Préparation du pack de distribution..."

# 1. Création de l'arborescence de release
mkdir -p $REL_DIR/bin
mkdir -p $REL_DIR/vectors
mkdir -p $REL_DIR/docs

# 2. Copie du noyau et des signatures
cp dist/SOVEREIGN-V7-GOLD.jar $REL_DIR/bin/ 2>/dev/null || cp release_final_gold/SOVEREIGN-V7-GOLD.jar $REL_DIR/bin/
cp release_final_gold/integrity.txt $REL_DIR/

# 3. Copie des vecteurs offensifs Python (Anti-Drone, Mesh, Air-Gap)
cp Sovereign-Offensive/vectors/infra_cloud/Sovereign_SkyGuard.py $REL_DIR/vectors/
cp core/Infiltration/Agent_Tools/Sovereign_Mesh_Tactical.py $REL_DIR/vectors/
cp Sovereign-Offensive/vectors/financial/AirGap_Exfiltrator.py $REL_DIR/vectors/

# 4. Intégration de la Doctrine
cp SOVEREIGN-OPS-MANUAL.md $REL_DIR/docs/
cp CERTIFICATE_FARDC_GOLD.txt $REL_DIR/docs/

# 5. Compression et Chiffrement
echo "[🔐] Chiffrement de l'archive (AES-256)..."
tar -czf - $REL_DIR | gpg -c --batch --passphrase "FARDC_SOUVERAINETE_2026" > ${EXPORT_NAME}.tar.gz.gpg

echo "================================================================"
echo "[✅] DISTRIBUTION PRÊTE : ${EXPORT_NAME}.tar.gz.gpg"
echo "[🔑] CLÉ DE DÉCHIFFREMENT : FARDC_SOUVERAINETE_2026"
echo "================================================================"
