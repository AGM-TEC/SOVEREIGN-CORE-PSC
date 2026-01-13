#!/bin/bash
# PROJECT: SOVEREIGN-CORE-PSC v35.2
# MODULE: SWITCH - FAILOVER RÉSEAU TACTIQUE

CHANNELS=("eth0" "sat0" "radio0")
C4ISR_SERVER="10.0.35.1"

check_connection() {
    ping -c 1 -W 2 $C4ISR_SERVER > /dev/null 2>&1
    return $?
}

while true; do
    for interface in "${CHANNELS[@]}"; do
        if check_connection; then
            echo "✅ Interface active: $interface"
            break
        fi
    done
    sleep 10
done
