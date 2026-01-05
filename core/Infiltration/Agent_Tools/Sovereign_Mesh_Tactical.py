import queue
import threading
import time

class TacticalMesh:
    def __init__(self):
        # File d'attente prioritaire : le plus petit nombre est traité en premier
        self.packet_queue = queue.PriorityQueue()
        self.is_running = True

    def add_packet(self, priority, data):
        """
        Ajoute un paquet au réseau.
        Priorité 1: Alerte Drone | 2: Finance | 3: Logs
        """
        print(f"[📤] Envoi (Prio:{priority}): {data[:20]}...")
        self.packet_queue.put((priority, time.time(), data))

    def transmitter(self):
        while self.is_running:
            if not self.packet_queue.empty():
                prio, timestamp, data = self.packet_queue.get()
                # Simulation de transmission P2P
                print(f"[📡] TRANSMISSION ACTIVE: [NIVEAU {prio}] - {data}")
                time.sleep(0.5) # Simule le délai radio

    def start(self):
        threading.Thread(target=self.transmitter, daemon=True).start()

# --- INITIALISATION TACTIQUE ---
mesh = TacticalMesh()
mesh.start()

# Exemple de flux de données mixte
mesh.add_packet(3, "Sync log #4421")
mesh.add_packet(1, "DRONE INTERCEPTÉ - SECTEUR NORD")
mesh.add_packet(2, "TX_FINANCE: +4.2 BTC")

time.sleep(3)
