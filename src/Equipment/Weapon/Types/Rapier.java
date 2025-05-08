package Equipment.Weapon.Types;

import Dice.Dice;
import Equipment.Weapon.Martial;

public class Rapier extends Martial {
    public Rapier() {
        super("Rapière", 1, new Dice(1, 8));
    }
}
