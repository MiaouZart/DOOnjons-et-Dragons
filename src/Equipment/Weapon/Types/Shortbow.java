package Equipment.Weapon.Types;

import Dice.Dice;
import Equipment.Weapon.Ranged;

public class Shortbow extends Ranged {
    public Shortbow() {
        super("Arc Court", 16, new Dice(1, 6));
    }
}
