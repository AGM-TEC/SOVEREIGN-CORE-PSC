cat <<'EOF' > ~/SOVEREIGN-CORE-PSC/Sovereign-Offensive/Signit_Stress_Test.py
import sys
import os
from core.engine import SignitInterceptor, StateMachine, SecurityAnomaly

def run_signit_audit():
    print("\n" + "📡"*30)
    print("   SOVEREIGN-CORE : AUDIT DE COMBAT SIGNIT (ROEM)")
    print("📡"*30)
    
    sm = StateMachine()
    signit = SignitInterceptor()
    results = {"PASS": 0, "FAIL": 0}

    # --- TEST 1 : ACTIVATION DU MODE SIGNIT ---
    print("\n[📶 TEST 1] Passage en mode SIGNIT_ACTIVE...")
    try:
        sm.set_mode("SIGNIT_ACTIVE")
        print("  [✅ PASS] Mode Signit engagé.")
        results["PASS"] += 1
    except SecurityAnomaly:
        results["FAIL"] += 1

    # --- TEST 2 : SCAN DE SPECTRE (PLAGE GSM/LTE) ---
    print("\n[📶 TEST 2] Simulation de scan de fréquences...")
    test_freqs = [900, 1800, 2100] # Fréquences valides
    for f in test_freqs:
        sig = signit.scan_spectrum(f)
        if "DETECTED" in sig:
            print(f"  [OK] Signal capturé sur {f} MHz.")
            results["PASS"] += 1

    # --- TEST 3 : DÉTECTION D'INTERFÉRENCE / HORS-PLAGE ---
    print("\n[📶 TEST 3] Test Anti-Jamming (Fréquences interdites)...")
    try:
        signit.scan_spectrum(5000) # Fréquence Wi-Fi non autorisée en mode GSM
        print("  [🚨 FAIL] Le système accepte des fréquences hors-plage !")
        results["FAIL"] += 1
    except SecurityAnomaly:
        print("  [✅ PASS] Fréquence hostile rejetée par le noyau.")
        results["PASS"] += 1

    print("\n" + "="*60)
    print(f" BILAN SIGNIT : {results['PASS']}/5 TESTS RÉUSSIS")
    print("="*60)

if __name__ == "__main__":
    run_signit_audit()
EOF
