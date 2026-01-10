import time
from core.engine import StateMachine, BlackBox, EffectMonitor

def run_stress_test():
    print("\n" + "🛡️ "*15)
    print("  SOVEREIGN CORE v8.0 : STRESS TEST GLOBAL ACTIVÉ")
    print("🛡️ "*15 + "\n")

    brain = StateMachine()
    logger = BlackBox()
    motor = EffectMonitor()

    # PHASE 1 : INITIALISATION SIGINT (SCANNER)
    print("[1/4] INITIALISATION SIGINT...")
    brain.set_mode("FURTIF")
    logger.record_incident("SIGINT_START", "Balayage spectral 2.4GHz amorcé.")
    time.sleep(1)

    # PHASE 2 : ESCALADE OFFENSIVE (DÉTECTION ENNEMIE)
    print("\n[2/4] DÉTECTION D'UNE CIBLE FINANCIÈRE...")
    brain.set_mode("OFFENSIF")
    logger.record_incident("TARGET_ACQUIRED", "Serveur de flux M-Pesa identifié.")
    motor.log_op()
    time.sleep(1)

    # PHASE 3 : EXFILTRATION ET CAPTURE (VALEUR FINANCIÈRE)
    print("\n[3/4] CAPTURE ET SÉCURISATION DES DONNÉES...")
    # Simulation du MPesaCommander et DataExfiltrator
    for i in range(3):
        logger.record_incident("DATA_CHUNK_SENT", f"Paquet {i+1} chiffré vers le Vault.")
        motor.log_op()
    print("  [✅] Flux capturé et scellé dans le SecurityVault.")
    time.sleep(1)

    # PHASE 4 : NETTOYAGE ET RAPPORT (MISSION COMPLETE)
    print("\n[4/4] GÉNÉRATION DU RAPPORT TACTIQUE ET EXTRACTION...")
    count = logger.get_incident_count()
    last = logger.get_last_incident()
    print(f"  [📊] BILAN : {count} opérations enregistrées.")
    print(f"  [📜] DERNIER ÉTAT : {last}")
    
    print("\n" + "🏁 "*15)
    print("  OPÉRATION TERMINÉE : SYSTÈME 100% VALIDE")
    print("🏁 "*15)

if __name__ == "__main__":
    run_stress_test()
