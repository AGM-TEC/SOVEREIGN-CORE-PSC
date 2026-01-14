#!/bin/bash
# MODULE: FINANCIAL PAYLOAD EXTRACTOR
# ROLE: Extraction du montant physique des flux USSD/SMS

echo -e "\e[1;32m🕵️ [MONOLITHE] Extracteur de montant activé sur le flux SDCCH...\e[0m"

# On utilise tshark pour lire le flux venant de gr-gsm
# On cherche spécifiquement la couche 'gsm_sms.sms_text'
tshark -i lo -Y "gsm_sms" -T fields -e gsm_sms.sms_text 2>/dev/null | while read -r line; do
    
    # 1. Vérification si la ligne contient une transaction
    if [[ $line =~ "Montant" || $line =~ "Amount" || $line =~ "transféré" ]]; then
        
        # 2. Extraction du montant physique (les chiffres)
        # On utilise grep pour isoler le nombre suivi de USD ou CDF
        AMOUNT=$(echo "$line" | grep -oP '\d+(\.\d+)?(?=\s*(USD|CDF|$))')
        CURRENCY=$(echo "$line" | grep -oP '(USD|CDF)')
        
        # 3. Affichage tactique pour l'État-Major
        echo -e "\n\e[1;31m💰 [INTERCEPTION FINANCIÈRE RÉELLE]\e[0m"
        echo -e "------------------------------------------------"
        echo -e "📄 MESSAGE BRUT : $line"
        echo -e "💵 MONTANT DÉTECTÉ : \e[1;32m$AMOUNT $CURRENCY\e[0m"
        echo -e "📍 SOURCE : [INTERCEPT_GSM_RDC_01]"
        echo -e "------------------------------------------------"
        
        # 4. Envoi au module de décision (Simulation d'alerte)
        if (( $(echo "$AMOUNT > 1000" | bc -l) )); then
             echo -e "\e[1;41m⚠️ ALERTE : FLUX HAUTE VALEUR - SUSPECT\e[0m"
        fi
    fi
done
