package Entity.Personnage.CharClass.Types;

import Entity.Personnage.CharClass.CharClass;
import Equipment.Weapon.Types.Quarterstaff;
import Equipment.Weapon.Types.Sling;
import Equipment.Equipment;

public class Wizard extends CharClass {
    public Wizard() {
        super("Magicien", 12, new Equipment[]{new Quarterstaff(), new Sling()});
    }
}
