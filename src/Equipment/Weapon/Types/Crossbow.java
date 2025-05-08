package Equipment.Weapon.Types;

import Dice.Dice;
import Equipment.Weapon.Ranged;

public class Crossbow extends Ranged {
    public Crossbow() {
        super("Arbalète", 16, new Dice(1, 8));
    }
}
