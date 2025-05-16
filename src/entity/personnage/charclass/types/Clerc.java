package entity.personnage.charclass.types;

import entity.personnage.charclass.CharClass;
import equipment.armor.types.ScaleMail;
import equipment.weapon.types.Crossbow;
import equipment.weapon.types.Mace;
import equipment.Equipment;

public class Clerc extends CharClass {
    public Clerc() {
        super("Clerc", 16, new Equipment[]{new Mace(), new ScaleMail(), new Crossbow()});
    }
}
