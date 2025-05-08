package Equipment.Weapon.Types;

import Dice.Dice;
import Equipment.Weapon.Simple;

public class Quarterstaff extends Simple {
    public Quarterstaff() {
        super("Bâton", 1, new Dice(1, 6));
    }
}
