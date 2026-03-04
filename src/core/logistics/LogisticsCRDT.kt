package core.logistics

class LogisticsCRDT {
    // Séparation stricte des incrémentations (ravitaillement) et décrémentations (tirs)
    private val added = mutableMapOf<String, Int>()
    private val removed = mutableMapOf<String, Int>()

    fun consumeAmmo(unitId: String, amount: Int) {
        // Action autorisée même sous dôme de brouillage (zéro connexion)
        val currentRemoved = removed.getOrDefault(unitId, 0)
        removed[unitId] = currentRemoved + amount
    }

    fun getAvailableAmmo(unitId: String): Int {
        return added.getOrDefault(unitId, 0) - (removed[unitId] ?: 0)
    }

    // La fonction de fusion mathématique (Merge) doit être appelée par la couche réseau
}
