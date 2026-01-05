import os
import random
import string

def mutate():
    target = "dist/SOVEREIGN-CORE-PSC.jar"
    if os.path.exists(target):
        with open(target, "ab") as f:
            noise = ''.join(random.choices(string.ascii_letters + string.digits, k=128))
            f.write(noise.encode())
        print(f"[🧬] Mutation appliquée sur {target}")
    else:
        print("[❌] Cible introuvable pour la mutation.")

if __name__ == "__main__":
    mutate()
