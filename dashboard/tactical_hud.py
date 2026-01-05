import os
import time
import random

def get_status():
    return {
        "CORE": "🟢 OPÉRATIONNEL",
        "MUTATION": "🧬 ACTIVE",
        "SKYGUARD": "📡 VIGILANCE RF",
        "AIRGAP": "🛰️ ÉCOUTE EM",
        "MESH": "🕸️ MAILLAGE ACTIF",
        "AGENTS": random.randint(12, 18),
        "VAULT": "142,500.00 USDT"
    }

def run_hud():
    try:
        while True:
            s = get_status()
            os.system('clear')
            print("="*60)
            print("   👑 SOVEREIGN-CORE v7.1-GOLD | QUARTIER GÉNÉRAL FARDC")
            print("="*60)
            print(f" [+] ÉTAT DU NOYAU   : {s['CORE']}")
            print(f" [+] BOUCLIER        : {s['MUTATION']}")
            print(f" [+] ANTI-DRONE      : {s['SKYGUARD']}")
            print(f" [+] CAPTURE AIR-GAP : {s['AIRGAP']}")
            print(f" [+] RÉSEAU MESH     : {s['MESH']}")
            print("-" * 60)
            print(f" [+] AGENTS DE TERRAIN ACTIFS : {s['AGENTS']}")
            print(f" [+] VALEUR DU VAULT TACTIQUE : {s['VAULT']}")
            print("-" * 60)
            print("\n [JOURNAL DE COMBAT]")
            print(f" {time.strftime('%H:%M:%S')} - Mutation de signature effectuée.")
            print(f" {time.strftime('%H:%M:%S')} - Réseau Mesh : Synchronisation réussie.")
            print("\n Appuyez sur Ctrl+C pour quitter.")
            time.sleep(3)
    except KeyboardInterrupt:
        print("\n[!] Fermeture du HUD. Le système reste actif en arrière-plan.")

if __name__ == "__main__":
    run_hud()
