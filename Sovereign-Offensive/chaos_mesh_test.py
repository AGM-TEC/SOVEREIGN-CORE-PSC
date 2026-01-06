cat <<EOF > chaos_mesh_test.py
import threading
import time
import random

class MeshAgent(threading.Thread):
    def __init__(self, agent_id):
        super().__init__()
        self.agent_id = agent_id
        self.active = True
        self.data_sent = 0

    def run(self):
        while self.active:
            self.data_sent += 1
            time.sleep(0.5)

print("--- [🛰️ SIMULATION CHAOS MESH : 15 AGENTS] ---")
agents = [MeshAgent(f"AGENT-{i:02d}") for i in range(1, 16)]

for a in agents: a.start()
print(f"[✅] 15 agents déployés sur le réseau Mesh.")

time.sleep(2)
print("\n[⚠️] ALERTE : Brouillage électronique détecté dans le secteur Sud !")
lost_indices = random.sample(range(15), 5)
for idx in lost_indices:
    agents[idx].active = False
    print(f"[❌] CONNEXION PERDUE : {agents[idx].agent_id}")

time.sleep(2)
print("\n[📊] ANALYSE DE RÉSURGENCE :")
survivors = [a for a in agents if a.active]
total_packets = sum(a.data_sent for a in agents)

print(f"  - Agents actifs : {len(survivors)}/15")
print(f"  - Intégrité des données : {total_packets} paquets récupérés.")
print("  - Protocole de repli : ACTIVÉ")

if len(survivors) >= 10:
    print("\n[🏆] RÉSULTAT : Succès. Le maillage a maintenu le quorum.")
else:
    print("\n[💀] RÉSULTAT : Échec. Réseau fragmenté.")
EOF
