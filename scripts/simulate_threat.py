import websocket
import json
import time
import random

# Connexion au WebSocket du Sovereign Core
ws = websocket.create_connection("ws://localhost:7070/api/signals/live")

print("📡 [SIMULATEUR] Injection d'un signal de drone DJI (2.4GHz)...")

try:
    while True:
        # Simulation d'un pic de puissance caractéristique d'un drone
        power = random.uniform(-20, -10) # Signal très fort (proximité)
        freq = 2412000000 # Fréquence standard WiFi/Drone
        
        data = json.dumps({"power": power, "freq": freq})
        ws.send(data)
        time.sleep(0.5)
except KeyboardInterrupt:
    ws.close()
    print("🛑 Simulation arrêtée.")
