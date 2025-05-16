package equipment.weapon.types;

import dice.Dice;
import equipment.weapon.Simple;

public class Quarterstaff extends Simple {
    public Quarterstaff() {
        super("Bâton", 1, new Dice(1, 6));
    }
}
