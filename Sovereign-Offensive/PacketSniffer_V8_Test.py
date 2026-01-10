import socket
import time
import threading
from core.engine import BlackBox

def simulate_enemy_traffic():
    """Simule un appareil ennemi envoyant des identifiants en clair"""
    time.sleep(1)
    try:
        with socket.create_connection(("127.0.0.1", 9999), timeout=2) as sock:
            sock.sendall(b"user=commander_rebel;pass=secret123\n")
            print("[🛰️] TRAFIC : Paquet envoyé vers le sniffer.")
    except:
        pass

def run_sniffer_audit():
    print("\n" + "📡 "*15)
    print("  SOVEREIGN CORE V8 : AUDIT PACKET-SNIFFER")
    print("📡 "*15 + "\n")

    logger = BlackBox()
    
    # Simulation du serveur d'écoute (Port 9999)
    def sniffer_loop():
        with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
            s.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
            s.bind(("127.0.0.1", 9999))
            s.listen()
            conn, addr = s.accept()
            with conn:
                data = conn.recv(1024).decode()
                if "pass" in data:
                    print(f"  [🎯] INTERCEPTION : {data.strip()}")
                    logger.record_incident("SNIFFER_DISCOVERY", "Identifiants capturés")

    # Lancement parallèle
    t_sniff = threading.Thread(target=sniffer_loop)
    t_traffic = threading.Thread(target=simulate_enemy_traffic)

    t_sniff.start()
    t_traffic.start()
    t_sniff.join()

    if logger.get_incident_count() > 0:
        print("\n  [✅ BILAN TACTIQUE] :")
        print("     - Détection de signatures : OK")
        print("     - Archivage BlackBox : OK")
        print("     - Valeur de renseignement : VALIDÉE")

if __name__ == "__main__":
    run_sniffer_audit()
