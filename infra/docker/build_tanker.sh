#!/bin/bash
# MODULE: DOCKER TANKER BUILD
# ROLE: Forge de l'image blindée v35.1

echo "🏗️ FORGE DE L'IMAGE TANKER v35.1..."
docker build -t sovereign-monolith:v35.1 -f infra/docker/Dockerfile .

echo "🔐 SCELLAGE ET SYNCHRONISATION..."
git add infra/docker/Dockerfile
git add infra/docker/build_tanker.sh
git commit -m "🏛️ INFRA : Forge de l'enveloppe Docker blindée (MDO Ready)"
git push origin v35-SOUVERAIN
