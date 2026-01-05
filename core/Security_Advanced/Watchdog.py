import os
import time
import subprocess

processes = ["MissionOrchestrator", "crypto_clipper.py", "DualForce.py"]

def monitor():
    while True:
        for p in processes:
            check = subprocess.getoutput(f"ps aux | grep {p} | grep -v grep")
            if not check:
                print(f"[⚠️] ALERTE : {p} à l'arrêt. Relance immédiate...")
                # Commande de relance automatique ici
        time.sleep(30)

if __name__ == "__main__":
    monitor()
