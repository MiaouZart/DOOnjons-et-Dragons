package equipment.weapon.types;

import dice.Dice;
import equipment.weapon.Martial;

public class Longsword extends Martial {
    public Longsword() {
        super("Épée Longue", 1, new Dice(1, 8));
    }
}
