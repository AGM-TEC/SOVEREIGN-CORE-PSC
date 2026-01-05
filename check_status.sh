#!/bin/bash
echo "================================================================"
echo "🛡️ RAPPORT D'ÉTAT SOVEREIGN-CORE v7.1"
echo "================================================================"

# 1. Vérification des binaires
if [ -f "build/classes/com/fardc/sigint/core/security/AntiDumpManager.class" ]; then
    echo -e "[✅] Cerveau (Kotlin) : Compilé et Blindé"
else
    echo -e "[❌] Cerveau (Kotlin) : Échec de compilation"
fi

# 2. Vérification de la Sentinelle
PS_WATCHDOG=$(pgrep -f GhostWatchdog.sh)
if [ ! -z "$PS_WATCHDOG" ]; then
    echo -e "[✅] Sentinelle (Watchdog) : ACTIVE (PID: $PS_WATCHDOG)"
else
    echo -e "[❌] Sentinelle (Watchdog) : INACTIVE"
fi

# 3. Vérification du Manuel
if [ ! -w "SOVEREIGN-OPS-MANUAL.md" ]; then
    echo -e "[✅] Manuel Opérationnel : Verrouillé (Lecture Seule)"
else
    echo -e "[⚠️] Manuel Opérationnel : Non sécurisé (Dévérouillé)"
fi

echo "================================================================"
