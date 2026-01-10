class StateMachine:
    def __init__(self):
        self.current_mode = "VEILLE"
    def set_mode(self, mode):
        self.current_mode = mode
        print(f"[BRAIN] Mode switch: {mode}")

class EffectMonitor:
    def __init__(self):
        self.ops_count = 0
    def log_op(self):
        self.ops_count += 1

class BlackBox:
    def __init__(self):
        self.history = []
    def record_incident(self, type, details):
        entry = f"[{type}] {details}"
        self.history.append(entry)
        print(f"[DATABASE] {entry}")
    def get_last_incident(self):
        return self.history[-1] if self.history else None
    def get_incident_count(self):
        return len(self.history)
