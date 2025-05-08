package Equipment.Weapon;

import Dice.Dice;

public abstract class Martial extends Weapon {
    public Martial(String name, int range, Dice damage) {
        super(name, range, damage, 2, 4);
    }
}
