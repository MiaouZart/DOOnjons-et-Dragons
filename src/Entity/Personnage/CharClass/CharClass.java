package Entity.Personnage.CharClass;

import Equipment.Equipment;

public abstract class CharClass {
    private final String m_name;
    private final int m_baseHealth;
    private final Equipment[] m_baseStuff;

    public CharClass(String name, int baseHealth, Equipment[] baseStuff) {
        m_baseHealth = baseHealth;
        m_baseStuff = baseStuff;
        m_name = name;
    }

    public int getBaseHealth() {
        return m_baseHealth;
    }

    public Equipment[] getBaseStuff() {
        return m_baseStuff;
    }

    public String getM_name() {
        return m_name;
    }
}
