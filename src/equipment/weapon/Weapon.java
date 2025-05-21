package equipment.weapon;
import dice.Dice;
import equipment.Equipment;
import equipment.EquipmentType;

import java.util.Objects;

import static dice.Dice.sumUp;

public abstract class Weapon extends Equipment {
    private final String m_name;
    private final int m_range;
    private final Dice m_damageDice;
    private final int m_speedModifier;
    private final int m_strengthModifier;
    protected EnumWeaponType m_type;
    private int m_bonusAtk = 0;
    private int m_bonusDam = 0;

    public Weapon(String name, int range, Dice damage, int speedModifier, int strengthModifier) {
        super(name, EquipmentType.WEAPON);
        m_name = name;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Weapon weapon = (Weapon) o;
        return Objects.equals(m_name, weapon.m_name) && m_range == weapon.m_range && m_speedModifier == weapon.m_speedModifier && m_strengthModifier == weapon.m_strengthModifier && Objects.equals(m_damageDice, weapon.m_damageDice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(m_range, m_damageDice, m_speedModifier, m_strengthModifier, m_name);
    }

    public EnumWeaponType getType(){
        return m_type;
    }

    public int damage(){
        return sumUp(m_damageDice.roll())+m_bonusDam;
    }

    public void addBonusAttack(int a) {
        m_bonusAtk += a;
    }

    public void addBonusDamage(int a) {
        m_bonusDam += a;
    }

    public int getBonusAttack() {
        return m_bonusAtk;
    }

    public int getBonusDamage() {
        return m_bonusDam;
    }
}
