package equipment.weapon.types;

import dice.Dice;
import equipment.weapon.Ranged;

public class Crossbow extends Ranged {
    public Crossbow() {
        super("Arbalète", 16, new Dice(1, 8));
    }
}
