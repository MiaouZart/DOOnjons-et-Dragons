package Equipment.Armor;
import Equipment.Equipment;

public abstract class Armor extends Equipment {
    private final int m_classVal;
    private final int m_speedModifier;

    public Armor(String name, int classVal, int speedModifier) {
        super(name);
        this.m_classVal = classVal;
        this.m_speedModifier = speedModifier;
    }

    public int getSpeedModifier() {
        return m_speedModifier;
    }

    public int getClassVal() {
        return m_classVal;
    }
}
