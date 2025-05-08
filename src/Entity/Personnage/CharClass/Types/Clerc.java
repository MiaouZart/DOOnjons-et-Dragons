package Entity.Personnage.CharClass.Types;

import Entity.Personnage.CharClass.CharClass;
import Equipment.Armor.Types.ScaleMail;
import Equipment.Weapon.Types.Crossbow;
import Equipment.Weapon.Types.Mace;
import Equipment.Equipment;

public class Clerc extends CharClass {
    public Clerc() {
        super("Clerc", 16, new Equipment[]{new Mace(), new ScaleMail(), new Crossbow()});
    }
}
