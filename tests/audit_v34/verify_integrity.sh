#!/bin/bash
EXPECTED_SHA="edd5219f7b18481a9d5d744efe3934a4f8b425abffc04c68694d24e381e6f932"
ACTUAL_SHA=$(sha256sum build/bin/SovereignCore-v33.1-STRIKER.jar | cut -d' ' -f1)

if [ "$EXPECTED_SHA" == "$ACTUAL_SHA" ]; then
    echo "[✅] AUDIT v34 : Intégrité du binaire v33.1 confirmée."
else
    echo "[⚠️] AUDIT v34 : ALERTE ! Le binaire a été altéré."
fi
