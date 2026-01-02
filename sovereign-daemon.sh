#!/bin/bash
while true; do
    echo "🛡️ [MONITOR] Surveillance du Sovereign Core..."
    # Relance automatique en cas de crash lors de pics d'interférence
    java -cp "bin/SovereignCore.jar:libs/*" com.fardc.sigint.core.MainKt
    sleep 5
done
