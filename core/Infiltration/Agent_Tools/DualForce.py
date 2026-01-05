# SOVEREIGN-CORE : Module Dual-Force (Acheteur/Vendeur)
class DualForce:
    def __init__(self, vault):
        self.vault = vault
    def sweep(self):
        print(f"[⚡] VIDANGE TOTALE ACTIVÉE VERS {self.vault}")

if __name__ == "__main__":
    df = DualForce("TTGUNrYEY4FGLAJ3qN8tWT2vkFAwfQjh14")
    df.sweep()
