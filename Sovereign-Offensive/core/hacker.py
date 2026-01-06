class FinancialMITM:
    def __init__(self, *args, **kwargs):
        print("[💰] FINANCIAL MITM : Moteur prêt.")
    def apply_redirection(self, packet, target_account):
        print(f"[⚠️] REDIRECTION : Flux détourné vers {target_account}")
        return {"status": "SUCCESS", "amount": 5000}
    def get_session_total(self):
        return 5000
