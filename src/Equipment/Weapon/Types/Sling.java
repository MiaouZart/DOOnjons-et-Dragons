package Equipment.Weapon.Types;

import Dice.Dice;
import Equipment.Weapon.Ranged;

public class Sling extends Ranged {
    public Sling() {
        super("Fronde", 6, new Dice(1, 4));
    }
}
