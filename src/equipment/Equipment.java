package equipment;

import equipment.weapon.EnumWeaponType;

public abstract class Equipment {
    private final String m_name;


    public Equipment(String name) {
        m_name = name;
    }

    public String getName() {
        return m_name;
    }

    @Override
    public String toString() {
        return m_name;
    }
}
