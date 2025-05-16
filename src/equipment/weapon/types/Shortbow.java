package equipment.weapon.types;

import dice.Dice;
import equipment.weapon.Ranged;

public class Shortbow extends Ranged {
    public Shortbow() {
        super("Arc Court", 16, new Dice(1, 6));
    }
}
