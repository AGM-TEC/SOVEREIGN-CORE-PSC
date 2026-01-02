#!/bin/bash

echo "--------------------------------------------------"
echo "🛡️  SOVEREIGN CORE v4.0 - DEPLOYMENT SEQUENCE"
echo "--------------------------------------------------"

# 1. Nettoyage des processus fantômes
echo "🧹 [1/3] Nettoyage du spectre (Libération port 7070)..."
pkill -9 java 2>/dev/null

# 2. Compilation de l'arsenal
echo "⚙️  [2/3] Compilation des modules tactiques..."
kotlinc src/main/kotlin/com/fardc/sigint/core/Main.kt \
        src/main/kotlin/com/fardc/sigint/core/CombatModeSelector.kt \
        src/main/kotlin/com/fardc/sigint/core/network/SdrBridge.kt \
        src/main/kotlin/com/fardc/sigint/core/network/SignalStreamer.kt \
        src/main/kotlin/com/fardc/sigint/core/hardware/SovereignHardware.kt \
        src/main/kotlin/com/fardc/sigint/services/dsp/SignalClassifier.kt \
        src/main/kotlin/com/fardc/sigint/core/sync/MeshSyncEngine.kt \
        src/main/kotlin/com/fardc/sigint/core/SecurityVault.kt \
        src/main/kotlin/com/fardc/sigint/core/CombatModeHandler.kt \
        -cp "libs/javalin.jar:libs/jetty-server.jar:libs/slf4j-api.jar" \
        -include-runtime -d bin/SovereignCore.jar

if [ $? -eq 0 ]; then
    echo "✅ [SUCCESS] Noyau compilé avec succès."
else
    echo "❌ [ERROR] Échec de la compilation. Vérifiez les dépendances."
    exit 1
fi

# 3. Lancement du système
echo "🚀 [3/3] Activation du SOVEREIGN CORE..."
echo "🌐 HUD disponible sur : http://localhost:7070/tactical/index.html"
echo "--------------------------------------------------"
java -cp "bin/SovereignCore.jar:libs/*" com.fardc.sigint.core.MainKt
