import time
import json
import websocket

def run_simulation():
    # Connexion au serveur Sovereign Core
    uri = "ws://localhost:7070/signal-stream"
    try:
        ws = websocket.create_connection(uri)
        print("--------------------------------------------------")
        print("🛡️  SOVEREIGN CORE v4.2 - FULL STACK TEST INITIATED")
        print("--------------------------------------------------")
        
        # 1. PHASE COMINT : Injection d'une signature numérique
        print("📡 [1/4] Injection signature COMINT...")
        drone_intel = {
            "type": "COMINT_DATA",
            "drone_id": "DJI-MAVIC-3-PRO-V4.2",
            "protocol": "OcuSync 3.0",
            "metadata": "Freq: 2.4GHz | Encryption: AES-NI"
        }
        ws.send(json.dumps(drone_intel))
        time.sleep(1.5)

        # 2. PHASE IA THREAT : Simulation d'une menace critique
        print("⚠️  [2/4] Calcul du score de menace IA...")
        threat_data = {
            "type": "THREAT_ANALYSIS",
            "power": -22.5,
            "signal_type": "DRONE_QUAD",
            "distance": 150.0
        }
        ws.send(json.dumps(threat_data))
        time.sleep(1.5)

        # 3. PHASE GEO-FUSION : Triangulation par 3 unités Mesh
        print("📍 [3/4] Envoi des vecteurs de triangulation (TDOA)...")
        geo_fusion = {
            "type": "GEO_FUSION",
            "sensors": [
                {"id": "UNIT_DELTA", "lat": -4.322, "lon": 15.311, "ts": 1641123456000},
                {"id": "UNIT_ALPHA", "lat": -4.331, "lon": 15.325, "ts": 1641123456045},
                {"id": "UNIT_GAMMA", "lat": -4.315, "lon": 15.332, "ts": 1641123456090}
            ]
        }
        ws.send(json.dumps(geo_fusion))
        time.sleep(1.5)

        # 4. PHASE MESH RELAY : Transmission en cascade
        print("🌐 [4/4] Test du relais Mesh (Multi-hop transmission)...")
        relay_packet = {
            "type": "MESH_RELAY",
            "origin_id": "POSTE_AVANCE_SUD",
            "hops": 3,
            "status": "FORWARDING_INTEL"
        }
        ws.send(json.dumps(relay_packet))

        print("\n✅ SIMULATION TERMINÉE : Vérifiez les logs du Sovereign Core.")
        ws.close()
        
    except Exception as e:
        print(f"❌ ERREUR TACTIQUE : Impossible de joindre le Core ({e})")

if __name__ == "__main__":
    run_simulation()
