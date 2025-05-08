package Equipment.Weapon;

import Dice.Dice;

public abstract class Simple extends Weapon {
    public Simple(String name, int range, Dice damage) {
        super(name, range, damage, 0, 0);
    }
}
