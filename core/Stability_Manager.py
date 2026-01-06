cat <<EOF > core/Stability_Manager.py
import subprocess
import time
import sys

processes = {
    "NOYAU_JAR": "java -jar dist/SOVEREIGN-CORE-PSC.jar",
    "MESH_V2": "python3 core/Infiltration/Agent_Tools/Sovereign_Mesh_Tactical_v2.py",
    "FIN_LINK": "python3 core/Infiltration/Sovereign_Financial_Link.py"
}

active_procs = {}

def start_process(name, command):
    # En mode debug, on laisse passer les erreurs pour voir le problème
    print(f"[🛡️] STABILITÉ : Tentative de lancement de {name}...")
    return subprocess.Popen(command.split())

try:
    for name, cmd in processes.items():
        active_procs[name] = start_process(name, cmd)
        time.sleep(2) # On laisse le temps au processus de démarrer

    while True:
        time.sleep(5)
        for name, proc in active_procs.items():
            status = proc.poll()
            if status is not None:
                print(f"[❌] ERREUR FATALE : {name} s'est arrêté (Code: {status})")
                # On ne relance pas immédiatement pour éviter la saturation
                time.sleep(10) 
                active_procs[name] = start_process(name, processes[name])

except KeyboardInterrupt:
    print("\n[🛑] Arrêt des systèmes...")
    for proc in active_procs.values():
        proc.terminate()
EOF
