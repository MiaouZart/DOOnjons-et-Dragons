package Equipment.Weapon.Types;

import Dice.Dice;
import Equipment.Weapon.Martial;

public class Longsword extends Martial {
    public Longsword() {
        super("Épée Longue", 1, new Dice(1, 8));
    }
}
