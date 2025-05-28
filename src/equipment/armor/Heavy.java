package equipment.armor;

public abstract class Heavy extends Armor {
    /**
     * Constructeur de la classe d'armure Lourde.
     * @param name Nom de l'armure.
     * @param classVal Valeur de classe d'armure.
     */
    public Heavy(String name, int classVal) {
        super(name, classVal, 4);
    }
}
