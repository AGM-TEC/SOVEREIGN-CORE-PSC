from core.engine import StateMachine, BlackBox
import time

def simulate_fuzzing_impact(target):
    brain = StateMachine()
    logger = BlackBox()
    
    print(f"\n[🔥] FUZZER-STRIKE : Déploiement des charges sur {target}...")
    brain.set_mode("OFFENSIF")
    
    # Simulation de la montée en charge
    for i in range(1, 4):
        print(f"  [⚡] Vague de saturation {i} injectée...")
        logger.record_incident("FUZZ_VAVE_SENT", f"Intensité {i*1000} paquets/s")
        time.sleep(0.5)

    print("\n[📊] RÉSULTAT : Service cible instable (Déni de Service partiel).")
    print(f"     Incidents enregistrés : {logger.get_incident_count()}")

if __name__ == "__main__":
    simulate_fuzzing_impact("10.0.0.15")
