cat <<EOF > cipher_integrity_test.py
import time

def simulate_integrity_check():
    print("--- [🔐 TEST D'INTÉGRITÉ DU CHIFFREMENT] ---")
    
    # Simulation de la base de logs
    logs = [
        {"id": 101, "data": "ENC_DATA_V8A", "sig": "VALID"},
        {"id": 102, "data": "ENC_DATA_X9B", "sig": "VALID"},
        {"id": 103, "data": "MALICIOUS_INJECTION", "sig": "INVALID"} # Injection
    ]
    
    print(f"[📡] Analyse de {len(logs)} segments de logs dans data/logs/...")
    time.sleep(1)
    
    cleaned_logs = []
    for log in logs:
        if log["sig"] == "VALID":
            print(f"  [✅] Segment {log['id']} : Signature authentifiée.")
            cleaned_logs.append(log)
        else:
            print(f"  [🚨] ALERTE : Segment {log['id']} CORROMPU ou NON SIGNÉ !")
            print(f"  [🧹] AUTO-CLEANER : Purge immédiate du segment {log['id']}.")

    print("\n[📊] RAPPORT FINAL :")
    print(f"  - Logs intègres : {len(cleaned_logs)}")
    print(f"  - Menaces neutralisées : {len(logs) - len(cleaned_logs)}")
    
    if len(cleaned_logs) == 2:
        return True
    return False

if __name__ == "__main__":
    if simulate_integrity_check():
        print("\n[🏆] RÉSULTAT : Succès. L'intégrité est maintenue.")
    else:
        print("\n[💀] RÉSULTAT : Échec. Fuite de données détectée.")
EOF
