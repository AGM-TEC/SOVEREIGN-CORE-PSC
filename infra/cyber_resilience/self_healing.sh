#!/bin/bash
# PROJECT: SOVEREIGN-CORE-PSC v35.2
# MODULE: CYBER-RESILIENCE (Self-Healing)

MONOLITH_PATH="$HOME/SOVEREIGN-CORE-PSC/build/bin/Sovereign_C4ISR_v35.1.jar"
CERT_PATH="$HOME/SOVEREIGN-CORE-PSC/build/bin/CERTIFICAT_V35_1.txt"

check_and_repair() {
    EXPECTED_HASH=$(cat "$CERT_PATH" | awk '{print $1}')
    if [ ! -f "$MONOLITH_PATH" ]; then
        git checkout -- "$MONOLITH_PATH"
        return
    fi
    CURRENT_HASH=$(sha256sum "$MONOLITH_PATH" | awk '{print $1}')
    if [ "$CURRENT_HASH" != "$EXPECTED_HASH" ]; then
        git checkout -- "$MONOLITH_PATH"
    fi
}

while true; do check_and_repair; sleep 30; done
