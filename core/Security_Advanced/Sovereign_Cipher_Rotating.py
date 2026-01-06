cat <<EOF > core/Security_Advanced/Sovereign_Cipher_Rotating.py
import hashlib
import time
import base64

class RollingCipher:
    def __init__(self, master_key="FARDC_SOUVERAINETE_2026"):
        self.master_key = master_key

    def _generate_current_key(self):
        # Génère une clé unique basée sur la minute actuelle
        # Change toutes les 60 secondes
        time_step = int(time.time() / 60)
        seed = f"{self.master_key}_{time_step}"
        return hashlib.sha256(seed.encode()).digest()

    def encrypt(self, message):
        key = self._generate_current_key()
        # Chiffrement XOR simple pour la rapidité du réseau Mesh
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

if __name__ == "__main__":
    cipher = RollingCipher()
    msg = "ALERTE_DRONE_COORD_44.12"
    encrypted = cipher.encrypt(msg)
    print(f"[🔐] Original: {msg}")
    print(f"[📡] Chiffré (Mesh): {encrypted}")
    print(f"[🔓] Déchiffré: {cipher.decrypt(encrypted)}")
EOF
