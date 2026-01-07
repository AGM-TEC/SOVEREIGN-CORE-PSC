cat <<'EOF' > ~/SOVEREIGN-CORE-PSC/Sovereign-Offensive/core/blackbox.py
import datetime
import hashlib

class BlackBox:
    __slots__ = ['log_file']

    def __init__(self, log_file="mission_audit.log"):
        self.log_file = log_file
        print("[📓] BLACKBOX : Journal d'audit haute sécurité activé.")

    def record_incident(self, event_type, details):
        timestamp = datetime.datetime.now().isoformat()
        raw_entry = f"{timestamp} | {event_type} | {details}"
        
        # Création d'une empreinte numérique (Hash) pour sécuriser l'entrée
        entry_hash = hashlib.sha256(raw_entry.encode()).hexdigest()[:16]
        sealed_entry = f"{raw_entry} | SEAL:{entry_hash}\n"
        
        with open(self.log_file, "a") as f:
            f.write(sealed_entry)
        
        print(f"[🔒] INCIDENT SCELLÉ : {event_type} enregistré dans la BlackBox.")
EOF
