#!/bin/bash
echo "[⚙️] SDR-OPTIMIZE : Configuration des permissions USB..."

# Désactiver le driver DVB-T par défaut qui bloque le SDR
if [ -f "/etc/modprobe.d/blacklist-rtl.conf" ]; then
    echo "Driver déjà blacklisted."
else
    echo "blacklist dvb_usb_rtl28xxu" | sudo tee /etc/modprobe.d/blacklist-rtl.conf
fi

echo "[✅] SDR-OPTIMIZE : Prêt pour l'interception haute performance."
