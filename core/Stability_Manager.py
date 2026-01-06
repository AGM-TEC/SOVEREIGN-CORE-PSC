cat <<EOF > core/Stability_Manager.py
import subprocess
import time
import os

processes = {
    "NOYAU_JAR": "java -jar dist/SOVEREIGN-CORE-PSC.jar",
    "MESH_V2": "python3 core/Infiltration/Agent_Tools/Sovereign_Mesh_Tactical_v2.py",
    "FIN_LINK": "python3 core/Infiltration/Sovereign_Financial_Link.py"
}

active_procs = {}

def start_process(name, command):
    print(f"[🛡️] STABILITÉ : Lancement de {name}...")
    return subprocess.Popen(command.split(), stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL)

print("="*60)
print("⚙️ GESTIONNAIRE DE STABILITÉ SOVEREIGN v8.2 ACTIVÉ")
print("="*60)

try:
    # Lancement initial
    for name, cmd in processes.items():
        active_procs[name] = start_process(name, cmd)

    while True:
        time.sleep(5)
        for name, proc in active_procs.items():
            if proc.poll() is not None:  # Le processus s'est arrêté
                print(f"[⚠️] ALERTE : {name} a crashé ! Relancement immédiat...")
                active_procs[name] = start_process(name, processes[name])
except KeyboardInterrupt:
    print("\n[🛑] Arrêt propre des services...")
    for proc in active_procs.values():
        proc.terminate()
EOF

chmod +x core/Stability_Manager.py
