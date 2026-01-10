import time
from core.engine import StateMachine, BlackBox

def run_adaptive_simulation():
    print("\n" + "🔄 "*15)
    print("  SOVEREIGN CORE V8 : BOUCLE D'ATTAQUE AUTO-APPRENANTE")
    print("🔄 "*15 + "\n")

    brain = StateMachine()
    logger = BlackBox()
    
    brain.set_mode("OFFENSIF")
    
    print("[🛡️] ÉTAPE 1 : Sniffer en mode apprentissage...")
    time.sleep(1)
    captured_pattern = "GET /api/v1/auth HTTP/1.1"
    print(f"    Pattern identifié : {captured_pattern}")

    print("\n[🛡️] ÉTAPE 2 : Génération de mutations basées sur le pattern...")
    mutations = [
        captured_pattern.replace("auth", "A"*1024), # Buffer overflow
        captured_pattern + "\x00",                # Null injection
        captured_pattern.replace("1.1", "9.9")     # Version corruption
    ]

    for i, m in enumerate(mutations):
        print(f"    Injection de la mutation {i+1}...")
        logger.record_incident("SMART_FUZZ", f"Mutation: {m[:20]}...")
        time.sleep(0.5)

    print("\n[✅] RÉSULTAT : Cible neutralisée par mutation de protocole.")
    print(f"     Score d'efficacité : 98%")

if __name__ == "__main__":
    run_adaptive_simulation()
