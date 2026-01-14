#!/bin/bash
# PROJECT: SOVEREIGN-CORE-PSC v35.2
# MODULE: SWITCH TACTIQUE (Failover Multi-Domaines)
# ROLE: Assurer la liaison C4ISR permanente

# Hiérarchie des interfaces (Priorité : Fibre > Satellite > Radio HF)
INTERFACES=("eth0" "sat0" "radio0")
C4ISR_GATEWAY="10.0.35.1" # IP du centre de commandement sécurisé

echo "📡 [SWITCH] Démarrage de la surveillance des liaisons..."

check_link() {
    ping -I "$1" -c 1 -W 2 "$C4ISR_GATEWAY" > /dev/null 2>&1
    return $?
}

while true; do
    LINK_FOUND=false
    for iface in "${INTERFACES[@]}"; do
        if check_link "$iface"; then
            if [ "$CURRENT_IFACE" != "$iface" ]; then
                echo "🔄 [SWITCH] Bascule critique : Liaison active via $iface"
                CURRENT_IFACE="$iface"
            fi
            LINK_FOUND=true
            break
        fi
    done

    if [ "$LINK_FOUND" = false ]; then
        echo "🚨 [ALERTE] RUPTURE TOTALE DE LIAISON ! Mode autonome activé."
    fi
    sleep 5
done
