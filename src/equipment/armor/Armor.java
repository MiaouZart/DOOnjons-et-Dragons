package equipment.armor;
import equipment.Equipment;
import equipment.EquipmentType;

import java.util.Objects;

public abstract class Armor extends Equipment {
    private final int m_classVal;
    private final int m_speedModifier;
    private final String m_name;

    /**
     * Constructeur de l'armure.
     * @param name Nom de l'armure
     * @param classVal Valeur de la classe d'amure (à battre pour faire des dégâts).
     * @param speedModifier Modificateur de vitesse infligé par l'armure.
     */
    public Armor(String name, int classVal, int speedModifier) {
        super(name, EquipmentType.ARMOR);
        m_name = name;
        this.m_classVal = classVal;
        this.m_speedModifier = speedModifier;
    }

    /**
     * Getter du modificateur de vitesse.
     * @return Modificateur de vitesse infligé par l'armure.
     */
    public int getSpeedModifier() {
        return m_speedModifier;
    }

    /**
     * Getter de la classe d'armure.
     * @return Classe d'armure (à battre pour faire des dégâts).
     */
    public int getClassVal() {
        return m_classVal;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Armor armor = (Armor) o;
        return m_classVal == armor.m_classVal && m_speedModifier == armor.m_speedModifier && Objects.equals(m_name, armor.m_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(m_classVal, m_speedModifier, m_name);
    }
}
