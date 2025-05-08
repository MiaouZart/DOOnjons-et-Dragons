package Equipment.Weapon.Types;

import Dice.Dice;
import Equipment.Weapon.Simple;

public class Mace extends Simple {
    public Mace() {
        super("Masse d'Armes", 1, new Dice(1, 6));
    }
}
