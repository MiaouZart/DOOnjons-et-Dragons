package equipment.weapon.types;

import dice.Dice;
import equipment.weapon.Martial;

public class Greatsword extends Martial {
    public Greatsword() {
        super("Épée à Deux Mains", 1, new Dice(2,6));
    }
}
