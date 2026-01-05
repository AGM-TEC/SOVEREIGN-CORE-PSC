cat <<EOF > core/Infiltration/Agent_Tools/Sovereign_Mesh_Tactical.py
import socket
import threading
import time

class TacticalMesh:
    def __init__(self, port=2404):
        self.port = port
        self.nodes = [] # Adresses des autres agents FARDC détectés
        self.identity = f"AGENT-FARDC-{os.getpid()}"

    def broadcast_alert(self, message):
        """Diffuse une alerte à tous les agents à portée Wi-Fi/LAN"""
        with socket.socket(socket.AF_INET, socket.SOCK_DGRAM) as s:
            s.setsockopt(socket.SOL_SOCKET, socket.SO_BROADCAST, 1)
            msg = f"[{self.identity}] ALERT: {message}"
            s.sendto(msg.encode(), ('<broadcast>', self.port))
            print(f"[📡] MESH : Alerte diffusée : {message}")

    def listen_mesh(self):
        """Écoute permanente des messages du réseau maillé"""
        with socket.socket(socket.AF_INET, socket.SOCK_DGRAM) as s:
            s.bind(('', self.port))
            while True:
                data, addr = s.recvfrom(1024)
                if addr[0] not in self.nodes:
                    print(f"[🤝] NOUVEAU NOEUD DÉTECTÉ : {addr[0]}")
                    self.nodes.append(addr[0])
                print(f"[📩] REÇU DE {addr[0]} : {data.decode()}")

if __name__ == "__main__":
    import os
    mesh = TacticalMesh()
    # Lancement de l'écoute en arrière-plan
    threading.Thread(target=mesh.listen_mesh, daemon=True).start()
    # Test de broadcast initial
    mesh.broadcast_alert("SYSTÈME MESH OPÉRATIONNEL - SOUVERAINETÉ TOTALE")
    while True: time.sleep(10)
EOF

chmod +x core/Infiltration/Agent_Tools/Sovereign_Mesh_Tactical.py
