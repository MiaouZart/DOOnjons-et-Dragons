package equipment.weapon.types;

import dice.Dice;
import equipment.weapon.Martial;

public class Rapier extends Martial {
    public Rapier() {
        super("Rapière", 1, new Dice(1, 8));
    }
}
