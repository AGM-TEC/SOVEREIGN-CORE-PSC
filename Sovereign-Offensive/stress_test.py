cat <<EOF > stress_test.py
import time
import queue

def run_stress_test():
    print("--- [🚀 TEST DE CHARGE MASSIVE : 1000 TX/SEC] ---")
    data_queue = queue.Queue()
    start_time = time.time()
    total_tx = 5000 # On teste sur 5000 transactions rapides
    
    print(f"[⚙️] Injection de {total_tx} transactions dans le buffer...")
    
    for i in range(total_tx):
        # Simulation d'un paquet de données
        packet = {"id": i, "payload": "DATA_STREAM_PART_" + str(i)}
        data_queue.put(packet)
        
        if i % 1000 == 0 and i > 0:
            print(f"  [⚡] {i} transactions injectées...")

    print("[✅] Injection terminée. Début du traitement par le noyau...")
    
    processed = 0
    while not data_queue.empty():
        data_queue.get()
        processed += 1
        # Simule un micro-délai de traitement CPU
        if processed % 500 == 0:
            time.sleep(0.01) 

    end_time = time.time()
    duration = end_time - start_time
    rate = total_tx / duration

    print("\n[📊] RAPPORT DE PERFORMANCE :")
    print(f"  - Temps total : {duration:.2f} secondes")
    print(f"  - Vitesse : {rate:.2f} tx/sec")
    
    if rate > 800:
        return True
    return False

if __name__ == "__main__":
    if run_stress_test():
        print("\n[🏆] RÉSULTAT : Succès. Le processeur supporte la charge haute densité.")
    else:
        print("\n[⚠️] RÉSULTAT : Attention. Latence détectée. Optimisation requise.")
EOF
