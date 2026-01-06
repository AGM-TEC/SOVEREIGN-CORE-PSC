cat <<EOF > core/Infiltration/Agent_Tools/Sovereign_Mesh_Tactical_v2.py
import queue
import threading
import time
import hashlib
import base64

class RollingCipher:
    def __init__(self, master_key="FARDC_SOUVERAINETE_2026"):
        self.master_key = master_key

    def _generate_current_key(self):
        time_step = int(time.time() / 60)
        seed = f"{self.master_key}_{time_step}"
        return hashlib.sha256(seed.encode()).digest()

    def encrypt(self, message):
        key = self._generate_current_key()
        encrypted = bytearray()
        for i in range(len(message)):
            encrypted.append(message.encode()[i] ^ key[i % len(key)])
        return base64.b64encode(encrypted).decode()

    def decrypt(self, encoded_message):
        key = self._generate_current_key()
        decoded = base64.b64decode(encoded_message)
        decrypted = bytearray()
        for i in range(len(decoded)):
            decrypted.append(decoded[i] ^ key[i % len(key)])
        return decrypted.decode()

class TacticalMesh:
    def __init__(self):
        self.packet_queue = queue.PriorityQueue()
        self.cipher = RollingCipher()
        self.is_running = True

    def send_secure_packet(self, priority, data):
        # Chiffrement automatique avant mise en file d'attente
        encrypted_data = self.cipher.encrypt(data)
        print(f"[🔐] Chiffrement Prio-{priority} effectué.")
        self.packet_queue.put((priority, time.time(), encrypted_data))

    def transmitter(self):
        print("[📡] ÉMETTEUR MESH SÉCURISÉ ACTIF")
        while self.is_running:
            if not self.packet_queue.empty():
                prio, timestamp, enc_data = self.packet_queue.get()
                # Simulation de réception et déchiffrement par un autre agent
                dec_data = self.cipher.decrypt(enc_data)
                print(f"\n[⚡] RECEPTION NIVEAU {prio}")
                print(f" >> RAW (Radio): {enc_data}")
                print(f" >> CLEAR (Agent): {dec_data}")
                time.sleep(0.8)
            time.sleep(0.1)

    def start(self):
        threading.Thread(target=self.transmitter, daemon=True).start()

if __name__ == "__main__":
    mesh = TacticalMesh()
    mesh.start()
    mesh.send_secure_packet(1, "ALERTE: Incursion drone - Secteur Goma")
    mesh.send_secure_packet(2, "PAYMENT: 50,000 CDF interceptés")
    time.sleep(4)
EOF
