package equipment.armor;

public abstract class Light extends Armor {
    /**
     * Constructeur de la classe d'armure Légère.
     * @param name Nom de l'armure.
     * @param classVal Valeur de classe d'armure.
     */
    public Light(String name, int classVal) {
        super(name, classVal, 0);
    }
}
