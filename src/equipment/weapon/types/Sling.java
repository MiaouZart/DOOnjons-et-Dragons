package equipment.weapon.types;

import dice.Dice;
import equipment.weapon.Ranged;

public class Sling extends Ranged {
    public Sling() {
        super("Fronde", 6, new Dice(1, 4));
    }
}
