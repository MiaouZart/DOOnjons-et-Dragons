package equipment;

public abstract class Equipment {
    private final String m_name;
    private final EquipmentType m_type;

    /**
     * Constructeur d'un équipement.
     * @param name Nom de l'équipement.
     * @param type Type de l'équipement.
     */
    public Equipment(String name, EquipmentType type) {
        m_name = name;
        m_type = type;
    }

    /**
     * Getter du nom de l'équipement.
     * @return Nom du personnage
     */
    public String getName() {
        return m_name;
    }

    /**
     * Getter du Type de l'Équipement.
     * @return Type de l'équipement.
     */
    public EquipmentType getEquipmentType() {
        return m_type;
    }

    @Override
    public String toString() {
        return m_name;
    }
}
