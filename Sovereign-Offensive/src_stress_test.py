cat <<'EOF' > ~/SOVEREIGN-CORE-PSC/Sovereign-Offensive/src_stress_test.py
import sys
import os
from core.engine import Gatekeeper, FinancialMITM, SecurityAnomaly, StateMachine

def test_src_integrity():
    print("\n" + "⚔️"*30)
    print("   SOVEREIGN-CORE : AUDIT D'INTÉGRATION SRC/")
    print("⚔️"*30)
    
    gate = Gatekeeper()
    hacker = FinancialMITM()
    sm = StateMachine()
    
    # 1. TEST DE L'ORCHESTRATION
    print("[1] Test de la chaîne de commandement (SRC -> CORE)...")
    try:
        sm.set_mode("OFFENSIF")
        print("  [✅ PASS] Mode offensif validé.")
    except Exception as e:
        print(f"  [🚨 FAIL] Erreur de transition : {e}")

    # 2. SIMULATION DE CORRUPTION DANS SRC
    print("[2] Simulation d'une commande corrompue venant de SRC/...")
    corrupted_payload = {"amount": "INVALID_SQL_INJECTION", "target": "REBELLE_HQ"}
    
    try:
        hacker.apply_redirection(corrupted_payload, "TRESOR_PUBLIC")
        print("  [🚨 FAIL] Le noyau a laissé passer une donnée brute de SRC !")
    except SecurityAnomaly:
        print("  [✅ PASS] Le Noyau a bloqué l'injection venant du module SRC.")
    except Exception as e:
        print(f"  [✅ PASS] Erreur capturée au niveau supérieur : {type(e).__name__}")

if __name__ == "__main__":
    test_src_integrity()
EOF
