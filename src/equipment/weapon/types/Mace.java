package equipment.weapon.types;

import dice.Dice;
import equipment.weapon.Simple;

public class Mace extends Simple {
    public Mace() {
        super("Masse d'Armes", 1, new Dice(1, 6));
    }
}
