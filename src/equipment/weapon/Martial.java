package equipment.weapon;

import dice.Dice;

public abstract class Martial extends Weapon {
    public Martial(String name, int range, Dice damage) {
        super(name, range, damage, 2, 4);
    }
}
