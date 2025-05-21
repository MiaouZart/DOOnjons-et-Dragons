package equipment;

import equipment.weapon.EnumWeaponType;

public abstract class Equipment {
    private final String m_name;
    private final EquipmentType m_type;

    public Equipment(String name, EquipmentType type) {
        m_name = name;
        m_type = type;
    }

    public String getName() {
        return m_name;
    }

    public EquipmentType getEquipmentType() {
        return m_type;
    }

    @Override
    public String toString() {
        return m_name;
    }
}
