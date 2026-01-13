#!/bin/bash
# MODULE: K8S CLUSTER SYNC
# ROLE: Déploiement du bouclier de grappe

echo "🛰️ SYNCHRONISATION DU CLUSTER C4ISR..."
git add infra/k8s/deployment.yaml
git add infra/k8s/sync_cluster.sh
git commit -m "🏛️ INFRA : Déploiement Kubernetes v35.1 (Cluster Ready)"
git push origin v35-SOUVERAIN
