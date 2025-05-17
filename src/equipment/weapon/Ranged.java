package equipment.weapon;

import dice.Dice;

public abstract class Ranged extends Weapon {
    public Ranged(String name, int range, Dice damage) {
        super(name, range, damage, 0, 0);
        m_type = EnumWeaponType.RANGE;
    }
}
