cat <<EOF > core/Stability_Manager.py
import subprocess
import time
import os
import sys

# Configuration de l'environnement Python
root_dir = os.getcwd()
env_config = os.environ.copy()
env_config["PYTHONPATH"] = root_dir

processes = {
    "NOYAU_JAR": "java -jar dist/SOVEREIGN-CORE-PSC.jar",
    "MESH_V2": f"python3 {root_dir}/core/Infiltration/Agent_Tools/Sovereign_Mesh_Tactical_v2.py",
    "FIN_LINK": f"python3 {root_dir}/core/Infiltration/Sovereign_Financial_Link.py"
}

active_procs = {}

def start_process(name, command):
    print(f"[🛡️] STABILITÉ : Lancement de {name}...")
    # On lance depuis la racine pour que les imports 'core.xyz' fonctionnent
    return subprocess.Popen(command.split(), env=env_config, cwd=root_dir)

print("="*60)
print("⚙️ GESTIONNAIRE DE STABILITÉ v8.3 - FULL PATH INJECTION")
print("="*60)

try:
    for name, cmd in processes.items():
        active_procs[name] = start_process(name, cmd)
        time.sleep(2)

    while True:
        time.sleep(5)
        for name, proc in active_procs.items():
            if proc.poll() is not None:
                print(f"[⚠️] ALERTE : {name} est inactif ! Code: {proc.returncode}. Relancement...")
                active_procs[name] = start_process(name, processes[name])
                time.sleep(2)
except KeyboardInterrupt:
    print("\n[🛑] Neutralisation...")
    for proc in active_procs.values():
        proc.terminate()
EOF
cat <<EOF > core/Auto_Cleaner.py
import os
import time

LOG_FILE = ".system_runtime.log"
MAX_SIZE_MB = 5  # Limite de sécurité avant effacement

def clean_logs():
    if os.path.exists(LOG_FILE):
        size = os.path.getsize(LOG_FILE) / (1024 * 1024)
        if size > MAX_SIZE_MB:
            with open(LOG_FILE, 'w') as f:
                f.write("[🧹] NETTOYAGE AUTOMATIQUE EXECUTE - SECURITE PREVENTIVE\n")
            print("[🛡️] LOGS PURGÉS POUR MAINTENIR LA FURTIVITÉ")

if __name__ == "__main__":
    while True:
        clean_logs()
        time.sleep(3600) # Vérification toutes les heures
EOF

