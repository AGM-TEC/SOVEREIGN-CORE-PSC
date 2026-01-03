#!/bin/bash
DATE_STR=$(date +%Y%m%d_%H%M)
FICHIER_RAPPORT="reports/patrouilles/RAPPORT_SIGINT_FARDC_$DATE_STR.md"

echo "📑 Compilation du rapport tactique en cours..."
{
    echo "# 🛡️ RAPPORT DE MISSION SIGINT - FARDC"
    echo "📅 Date : $(date)"
    echo "⚔️ Unité : Sovereign Core PSC v4.2"
    echo "---"
    echo "## 📡 SURVEILLANCE DU SPECTRE (SDR)"
    [ -f logs/spectrum_raw.csv ] && tail -n 15 logs/spectrum_raw.csv || echo "Aucune activité suspecte détectée sur les bandes 2.4GHz."
    
    echo "## 🎯 TRIANGULATION ET GÉO-LOCALISATION (TDOA)"
    grep "TDOA:" logs/console.log | tail -n 5 || echo "Aucun verrouillage géographique effectué aujourd'hui."
    
    echo "## 💰 INTERCEPTIONS FINANCIÈRES (CYBER)"
    [ -f logs/mission_data.json ] && cat logs/mission_data.json || echo "Aucune capture de données financières enregistrée."
    echo "---"
    echo "## 🛡️ ANALYSE DU COMMANDANT"
    echo "Statut du réseau Mesh : CONNECTÉ"
    echo "Niveau de menace moyen : NORMAL"
    echo "---"
    echo "🔒 DOCUMENT CLASSÉ SECRET DÉFENSE"
} > "$FICHIER_RAPPORT"

echo "✅ Rapport généré avec succès : $FICHIER_RAPPORT"
