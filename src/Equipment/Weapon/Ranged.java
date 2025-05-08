package Equipment.Weapon;

import Dice.Dice;

public abstract class Ranged extends Weapon {
    public Ranged(String name, int range, Dice damage) {
        super(name, range, damage, 0, 0);
    }
}
