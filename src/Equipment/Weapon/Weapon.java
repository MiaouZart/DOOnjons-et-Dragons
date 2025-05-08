package Equipment.Weapon;
import Dice.Dice;
import Equipment.Equipment;

public abstract class Weapon extends Equipment {
    private final int m_range;
    private final Dice m_damageDice;
    private final int m_speedModifier;
    private final int m_strengthModifier;

    public Weapon(String name, int range, Dice damage, int speedModifier, int strengthModifier) {
        super(name);
        m_range = range;
        m_damageDice = damage;
        m_speedModifier = speedModifier;
        m_strengthModifier = strengthModifier;
    }

    public int getRange() {
        return m_range;
    }

    public Dice getDamageDice() {
        return m_damageDice;
    }

    public int getSpeedModifier() {
        return m_speedModifier;
    }

    public int getStrengthModifier() {
        return m_strengthModifier;
    }
}
