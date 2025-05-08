package Entity.Personnage.CharClass.Types;

import Entity.Personnage.CharClass.CharClass;
import Equipment.Equipment;
import Equipment.Weapon.Types.Rapier;
import Equipment.Weapon.Types.Shortbow;

public class Rogue extends CharClass {
    public Rogue() {
        super("Roublard", 16, new Equipment[]{new Rapier(), new Shortbow()});
    }
}
